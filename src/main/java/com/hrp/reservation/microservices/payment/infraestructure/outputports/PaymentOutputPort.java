package com.hrp.reservation.microservices.payment.infraestructure.outputports;

import com.hrp.reservation.microservices.payment.domain.Payment;

public interface PaymentOutputPort {
    Payment save(Payment payment);
}
