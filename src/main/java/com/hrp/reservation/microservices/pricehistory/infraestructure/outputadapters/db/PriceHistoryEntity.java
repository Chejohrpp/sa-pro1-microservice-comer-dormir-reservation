package com.hrp.reservation.microservices.pricehistory.infraestructure.outputadapters.db;

import com.hrp.reservation.microservices.pricehistory.domain.PriceHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistoryEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static PriceHistoryEntity from(PriceHistory priceHistory) {
        return new PriceHistoryEntity(
                priceHistory.getId(),
                priceHistory.getPrice(),
                priceHistory.getCreateAt()
        );
    }
}
