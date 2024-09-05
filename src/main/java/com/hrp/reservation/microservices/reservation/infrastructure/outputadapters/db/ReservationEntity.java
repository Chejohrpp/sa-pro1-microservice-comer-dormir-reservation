package com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String hotel;

    @Column(nullable = false)
    private String usernameClient;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @Column(nullable = false)
    private double price;

    public ReservationEntity(String roomNumber, String hotel, String usernameClient, ReservationStatus status, LocalDateTime checkInDate, LocalDateTime checkOutDate, double price) {
        this.roomNumber = roomNumber;
        this.hotel = hotel;
        this.usernameClient = usernameClient;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.price = price;
    }


    // Converts from Reservation domain model to ReservationEntity
    public static ReservationEntity from(Reservation reservation) {
        return new ReservationEntity(
                reservation.getRoomNumber(),
                reservation.getHotel(),
                reservation.getUsernameClient(),
                reservation.getStatus(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getTotalAmount()
        );
    }

    // Converts from ReservationEntity to Reservation domain model
    public Reservation toDomain() {
        return Reservation.builder()
                .id(id)
                .roomNumber(roomNumber)
                .hotel(hotel)
                .usernameClient(usernameClient)
                .status(status)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .totalAmount(price)
                .build();
    }


}
