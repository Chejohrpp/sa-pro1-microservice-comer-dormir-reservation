package com.hrp.reservation.microservices.payment.application.paymentchekoutusecase;

import com.hrp.reservation.microservices.payment.domain.PaymentMethod;
import lombok.Value;

@Value
public class PaymentCheckOutRequest {
    private Double amount;
    private PaymentMethod paymentMethod;
}
