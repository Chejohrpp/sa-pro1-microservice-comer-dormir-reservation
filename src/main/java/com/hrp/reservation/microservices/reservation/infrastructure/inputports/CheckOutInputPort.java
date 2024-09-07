package com.hrp.reservation.microservices.reservation.infrastructure.inputports;

import com.hrp.reservation.microservices.reservation.application.checkoutusecase.CheckOutRequest;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import jakarta.persistence.EntityNotFoundException;

public interface CheckOutInputPort {
    Reservation checkOutClient(long reservationId, CheckOutRequest checkOutRequest) throws IllegalArgumentException, EntityNotFoundException;
}
