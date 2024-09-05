package com.hrp.reservation.microservices.pricehistory.domain;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceHistory {
    private Long id;
    private Reservation reservation;
    private double price;
    private LocalDateTime createAt;
}
