package com.hrp.reservation.microservices.payment.domain;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Payment {
    private Long id;
    private Reservation reservation;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private PaymentMethod method;
}
