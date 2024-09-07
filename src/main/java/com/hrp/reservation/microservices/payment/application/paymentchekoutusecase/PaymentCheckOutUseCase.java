package com.hrp.reservation.microservices.payment.application.paymentchekoutusecase;

import com.hrp.reservation.microservices.common.annotation.UseCase;
import com.hrp.reservation.microservices.payment.domain.Payment;
import com.hrp.reservation.microservices.payment.domain.PaymentStatus;
import com.hrp.reservation.microservices.payment.infraestructure.inputports.PaymentInputport;
import com.hrp.reservation.microservices.payment.infraestructure.outputports.PaymentOutputPort;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UseCase
@Transactional
public class PaymentCheckOutUseCase implements PaymentInputport {
    private final PaymentOutputPort paymentOutputPort;

    @Autowired
    public PaymentCheckOutUseCase(PaymentOutputPort paymentOutputPort) {
        this.paymentOutputPort = paymentOutputPort;
    }

    @Override
    public void savePayment(Reservation reservation, PaymentCheckOutRequest paymentCheckOutRequest) {
        Payment payment =  Payment.builder().build();
        payment.setReservation(reservation);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(paymentCheckOutRequest.getAmount());
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setMethod(paymentCheckOutRequest.getPaymentMethod());
        paymentOutputPort.save(payment);
    }
}
