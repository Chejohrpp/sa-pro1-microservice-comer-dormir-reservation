package com.hrp.reservation.microservices.payment.infraestructure.outputadapter.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPaymentEntityRepository extends JpaRepository<PaymentEntity, Long> {
}
