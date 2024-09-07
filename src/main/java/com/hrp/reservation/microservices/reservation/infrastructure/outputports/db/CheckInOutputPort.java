package com.hrp.reservation.microservices.reservation.infrastructure.outputports.db;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;

import java.util.Optional;

public interface CheckInOutputPort {
    Optional<Reservation> findById(Long id);
}
