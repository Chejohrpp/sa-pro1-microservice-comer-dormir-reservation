package com.hrp.reservation.microservices.reservation.infrastructure.inputports;

import com.hrp.reservation.microservices.common.exceptions.AlreadyExistsException;
import com.hrp.reservation.microservices.reservation.application.reservationclientusecase.ReservationClientRequest;
import jakarta.persistence.EntityNotFoundException;

public interface ReservationClientInputPort {
    void ReservationRoom(String hotelid, String roomNumber, ReservationClientRequest reservationClientRequest) throws EntityNotFoundException, AlreadyExistsException;
}
