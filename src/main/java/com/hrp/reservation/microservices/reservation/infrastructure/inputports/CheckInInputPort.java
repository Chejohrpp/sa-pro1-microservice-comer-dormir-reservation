package com.hrp.reservation.microservices.reservation.infrastructure.inputports;

import jakarta.persistence.EntityNotFoundException;

public interface CheckInInputPort {
    void checkInclient(Long reservationId) throws EntityNotFoundException, IllegalArgumentException;
}
