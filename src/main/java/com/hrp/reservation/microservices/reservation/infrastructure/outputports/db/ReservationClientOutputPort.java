package com.hrp.reservation.microservices.reservation.infrastructure.outputports.db;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;

public interface ReservationClientOutputPort {
    ReservationEntity save(Reservation reservation);

    boolean isReservation(Reservation reservation);
}
