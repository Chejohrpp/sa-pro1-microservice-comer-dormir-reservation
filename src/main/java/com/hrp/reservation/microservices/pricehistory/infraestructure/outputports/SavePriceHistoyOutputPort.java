package com.hrp.reservation.microservices.pricehistory.infraestructure.outputports;

import com.hrp.reservation.microservices.pricehistory.domain.PriceHistory;

public interface SavePriceHistoyOutputPort{

    void savePriceHistoy(PriceHistory priceHistory);
}
