package com.hrp.reservation.microservices.reservation.infrastructure.inputadapters.restapi;

import lombok.Value;

@Value
public class ReservationClientResponse {
    String message;

    public static ReservationClientResponse from(String message) {
        return new ReservationClientResponse(message);
    }
}
