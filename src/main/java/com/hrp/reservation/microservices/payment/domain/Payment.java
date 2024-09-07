package com.hrp.reservation.microservices.payment.domain;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Payment {
    private Long id;
    private Reservation reservation;
    private Double amount;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentMethod method;
}
