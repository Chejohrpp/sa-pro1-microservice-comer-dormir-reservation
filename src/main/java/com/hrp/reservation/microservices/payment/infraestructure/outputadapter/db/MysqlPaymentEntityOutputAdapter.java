package com.hrp.reservation.microservices.payment.infraestructure.outputadapter.db;

import com.hrp.reservation.microservices.common.annotation.PersistenceAdapter;
import com.hrp.reservation.microservices.payment.domain.Payment;
import com.hrp.reservation.microservices.payment.infraestructure.outputports.PaymentOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceAdapter
public class MysqlPaymentEntityOutputAdapter implements PaymentOutputPort {
    private final JpaPaymentEntityRepository jpaPaymentEntityRepository;

    @Autowired
    public MysqlPaymentEntityOutputAdapter(JpaPaymentEntityRepository jpaPaymentEntityRepository) {
        this.jpaPaymentEntityRepository = jpaPaymentEntityRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntity.from(payment);
        return jpaPaymentEntityRepository.save(paymentEntity).toDomain(payment.getReservation());
    }
}
