package com.hrp.reservation.microservices.reservation.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Reservation {
    private Long id;
    private String roomNumber;
    private String hotel;
    private String usernameClient;
    private ReservationStatus status;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private double totalAmount;

}
