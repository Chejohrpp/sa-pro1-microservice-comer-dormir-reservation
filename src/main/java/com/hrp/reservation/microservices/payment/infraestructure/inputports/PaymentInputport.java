package com.hrp.reservation.microservices.payment.infraestructure.inputports;

import com.hrp.reservation.microservices.payment.application.paymentchekoutusecase.PaymentCheckOutRequest;
import com.hrp.reservation.microservices.payment.domain.Payment;
import com.hrp.reservation.microservices.reservation.domain.Reservation;

public interface PaymentInputport {
    void savePayment(Reservation reservation, PaymentCheckOutRequest paymentCheckOutRequest);
}
