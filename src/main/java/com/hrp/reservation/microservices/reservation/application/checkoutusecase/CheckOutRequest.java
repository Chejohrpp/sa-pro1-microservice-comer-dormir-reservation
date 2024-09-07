package com.hrp.reservation.microservices.reservation.application.checkoutusecase;

import com.hrp.reservation.microservices.payment.application.paymentchekoutusecase.PaymentCheckOutRequest;
import com.hrp.reservation.microservices.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutRequest {

    List<PaymentCheckOutRequest> payments;
}
