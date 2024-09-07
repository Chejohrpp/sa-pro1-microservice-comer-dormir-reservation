package com.hrp.reservation.microservices.reservation.application.checkinusecase;

import com.hrp.reservation.microservices.common.annotation.UseCase;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.CheckInInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.CheckInOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.ReservationClientOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.ChangeRoomState;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CheckInUseCase implements CheckInInputPort {
    private final CheckInOutputPort checkInOutputPort;
    private final ReservationClientOutputPort reservationClientOutputPort;
    private final ChangeRoomState changeRoomState;

    @Autowired
    public CheckInUseCase(CheckInOutputPort checkInOutputPort, ReservationClientOutputPort reservationClientOutputPort, ChangeRoomState changeRoomState) {
        this.checkInOutputPort = checkInOutputPort;
        this.reservationClientOutputPort = reservationClientOutputPort;
        this.changeRoomState = changeRoomState;
    }

    @Override
    public void checkInclient(Long reservationId) throws EntityNotFoundException, IllegalArgumentException {
        //validate if reservation exists
        Reservation reservation = checkInOutputPort.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("reservation not found"));

        //change the status
        reservation.setStatus(ReservationStatus.RESERVED);

        //change the available status room
        if (!changeRoomState.updateAvailable(reservation.getHotel(), reservation.getRoomNumber(),false)){
            throw new IllegalArgumentException("change room state not successfully change state");
        }

        //update the reservation
        reservation = reservationClientOutputPort.save(reservation);
    }
}
