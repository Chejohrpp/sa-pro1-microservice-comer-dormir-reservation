package com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db;

import com.hrp.reservation.microservices.common.annotation.PersistenceAdapter;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.CheckInOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.CheckOutOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.ReservationClientOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@PersistenceAdapter
public class MySqlReservationEntityOutputAdapter implements ReservationClientOutputPort, CheckInOutputPort, CheckOutOutputPort {
    private final JpaReservationEntityRepository jpaReservationEntityRepository;

    @Autowired
    public MySqlReservationEntityOutputAdapter(JpaReservationEntityRepository jpaReservationEntityRepository) {
        this.jpaReservationEntityRepository = jpaReservationEntityRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = ReservationEntity.from(reservation);
        return jpaReservationEntityRepository.save(reservationEntity).toDomain();
    }

    /**
     * Validates if the room is available for the given reservation.
     *
     * @param reservation the reservation to validate
     * @return true if the room is available, false otherwise
     */
    @Override
    public boolean isReservation(Reservation reservation) {
        // Fetch all reservations for the same hotel and room number where the status is not CANCELLED
        List<ReservationEntity> existingReservations = jpaReservationEntityRepository
                .findByHotelAndRoomNumberAndStatusNot(
                        reservation.getHotel(),
                        reservation.getRoomNumber(),
                        ReservationStatus.CANCELLED
                );
        // Iterate over existing reservations and check for date overlap
        for (ReservationEntity existingReservation : existingReservations) {
            if (isOverlapping(reservation.getCheckInDate(), reservation.getCheckOutDate(),
                    existingReservation.getCheckInDate(), existingReservation.getCheckOutDate())) {
                return true; // There's a conflict with another reservation
            }
        }
        return false;
    }

    /**
     * Helper method to check if two date ranges overlap.
     *
     * @param checkIn1  check-in date of the first reservation
     * @param checkOut1 check-out date of the first reservation
     * @param checkIn2  check-in date of the second reservation
     * @param checkOut2 check-out date of the second reservation
     * @return true if the date ranges overlap, false otherwise
     */
    private boolean isOverlapping(LocalDateTime checkIn1, LocalDateTime checkOut1,
                                  LocalDateTime checkIn2, LocalDateTime checkOut2) {
        return (checkIn1.isBefore(checkOut2) && checkOut1.isAfter(checkIn2));
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaReservationEntityRepository.findById(id)
                .map(ReservationEntity:: toDomain);
    }
}
