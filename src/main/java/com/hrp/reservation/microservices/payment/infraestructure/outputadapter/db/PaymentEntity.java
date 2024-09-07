package com.hrp.reservation.microservices.payment.infraestructure.outputadapter.db;

import com.hrp.reservation.microservices.payment.domain.Payment;
import com.hrp.reservation.microservices.payment.domain.PaymentMethod;
import com.hrp.reservation.microservices.payment.domain.PaymentStatus;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long reservation;

    private Double amount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    public PaymentEntity(long reservation, Double amount, LocalDate paymentDate, PaymentStatus status, PaymentMethod method) {
        this.reservation = reservation;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
        this.method = method;
    }

    public static PaymentEntity from(Payment payment) {
        return new PaymentEntity(
                payment.getReservation().getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getStatus(),
                payment.getMethod()
        );
    }

    public Payment toDomain(Reservation reservation){
        return Payment.builder()
                .id(id)
                .reservation(reservation)
                .amount(amount)
                .paymentDate(paymentDate)
                .status(status)
                .method(method)
                .build();
    }


}
