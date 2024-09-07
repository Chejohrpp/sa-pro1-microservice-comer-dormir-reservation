package com.hrp.reservation.microservices.reservation.infrastructure.outputports.db;

import com.hrp.reservation.microservices.reservation.domain.Reservation;

import java.util.Optional;

public interface CheckOutOutputPort {
    Optional<Reservation> findById(Long id);
}
