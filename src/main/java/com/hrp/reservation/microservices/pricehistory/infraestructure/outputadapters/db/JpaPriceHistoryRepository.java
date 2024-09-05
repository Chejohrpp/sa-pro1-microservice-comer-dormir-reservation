package com.hrp.reservation.microservices.pricehistory.infraestructure.outputadapters.db;

import com.hrp.reservation.microservices.pricehistory.domain.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceHistoryRepository extends JpaRepository<PriceHistoryEntity, Long> {
}
