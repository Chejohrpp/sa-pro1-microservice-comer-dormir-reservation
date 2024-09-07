package com.hrp.reservation.microservices.reservation.application.reservationclientusecase;

import com.hrp.reservation.microservices.common.annotation.UseCase;
import com.hrp.reservation.microservices.common.exceptions.AlreadyExistsException;
import com.hrp.reservation.microservices.pricehistory.domain.PriceHistory;
import com.hrp.reservation.microservices.pricehistory.infraestructure.outputports.SavePriceHistoyOutputPort;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.ReservationClientInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.ReservationClientOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.CheckRoomPriceOutputPort;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;

@UseCase
public class ReservationClientUseCase implements ReservationClientInputPort {
    private final ReservationClientOutputPort reservationClientOutputPort;
    private final CheckRoomPriceOutputPort checkRoomPriceOutputPort;
    private final SavePriceHistoyOutputPort savePriceHistoyOutputPort;

    @Autowired
    public ReservationClientUseCase(ReservationClientOutputPort reservationClientOutputPort, CheckRoomPriceOutputPort checkRoomPriceOutputPort, SavePriceHistoyOutputPort savePriceHistoyOutputPort) {
        this.reservationClientOutputPort = reservationClientOutputPort;
        this.checkRoomPriceOutputPort = checkRoomPriceOutputPort;
        this.savePriceHistoyOutputPort = savePriceHistoyOutputPort;
    }

    @Override
    public void ReservationRoom(String hotelid,
                                String roomNumber,
                                ReservationClientRequest reservationClientRequest) throws EntityNotFoundException, AlreadyExistsException, IllegalArgumentException {
        //validate
        //verify the room existed in the current hotel (output port) and get the price
        if (!checkRoomPriceOutputPort.existsByHotel(hotelid, roomNumber)){
            throw new  EntityNotFoundException (
                    String.format("Not records of existed the room %s in the hotel %s", roomNumber, hotelid)
            );
        }


        //to domain
        Reservation reservation = reservationClientRequest.toDomain(hotelid, roomNumber);

        //verify the dates
        validateDates(reservation.getCheckInDate(), reservation.getCheckOutDate());

        //verify if is availability in our db
        if(reservationClientOutputPort.isReservation(reservation)){
            throw new AlreadyExistsException("already taken the dates selected");
        }

        // call the priceHistory to storage the price
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setReservation(reservation);
        priceHistory.setCreateAt(LocalDateTime.now());

        //get the price
        priceHistory.setPrice(checkRoomPriceOutputPort.getPriceRoom(hotelid, roomNumber));

        //calculate the total_price
        reservation.setTotalAmount(calculateTotalAmount(reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                priceHistory.getPrice()));

        //save the reservation
        reservation.setStatus(ReservationStatus.PENDING);
        reservation = reservationClientOutputPort.save(reservation);
        priceHistory.setId(reservation.getId());

        //save the price of the history
        savePriceHistoyOutputPort.savePriceHistoy(priceHistory);
    }

    // Method to calculate total days
    public long calculateTotalDays(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return Duration.between(checkInDate, checkOutDate).toDays();
    }

    // Method to calculate total amount based on price per day from PriceHistory
    public double calculateTotalAmount(LocalDateTime checkInDate, LocalDateTime checkOutDate,double pricePerDay) {
        long totalDays = calculateTotalDays(checkInDate, checkOutDate);
        return totalDays * pricePerDay;
    }

    /**
     * Validates that checkInDate is earlier than checkOutDate and
     * that there is at least a 1-day difference between them.
     *
     * @param checkInDate  the check-in date
     * @param checkOutDate the check-out date
     * @return true if the dates are valid, false otherwise
     * @throws IllegalArgumentException if validation fails
     */
    public boolean validateDates(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null.");
        }

        // Check that check-in is before check-out
        if (!checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }

        // Check that the difference is at least 1 day
        long daysBetween = Duration.between(checkInDate, checkOutDate).toDays();
        if (daysBetween < 1) {
            throw new IllegalArgumentException("There must be at least 1 day difference between check-in and check-out.");
        }

        return true;
    }

}
