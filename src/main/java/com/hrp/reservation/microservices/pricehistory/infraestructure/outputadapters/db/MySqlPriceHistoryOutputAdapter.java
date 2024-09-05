package com.hrp.reservation.microservices.pricehistory.infraestructure.outputadapters.db;

import com.hrp.reservation.microservices.common.annotation.PersistenceAdapter;
import com.hrp.reservation.microservices.pricehistory.domain.PriceHistory;
import com.hrp.reservation.microservices.pricehistory.infraestructure.outputports.SavePriceHistoyOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

@PersistenceAdapter
public class MySqlPriceHistoryOutputAdapter implements SavePriceHistoyOutputPort {
    private final JpaPriceHistoryRepository jpaPriceHistoryRepository;

    @Autowired
    public MySqlPriceHistoryOutputAdapter(JpaPriceHistoryRepository jpaPriceHistoryRepository) {
        this.jpaPriceHistoryRepository = jpaPriceHistoryRepository;
    }

    @Override
    public void savePriceHistoy(PriceHistory priceHistory) {
        PriceHistoryEntity priceHistoryEntity = PriceHistoryEntity.from(priceHistory);
        jpaPriceHistoryRepository.save(priceHistoryEntity);
    }
}
