package com.hrp.reservation.microservices.reservation.application.reservationclientusecase;

import com.hrp.reservation.microservices.reservation.domain.Reservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationClientRequest {
    String usernameclient;
    LocalDateTime startdate;
    LocalDateTime enddate;

    public Reservation toDomain(String hotelId, String RoomNumber) {
        return Reservation.builder()
                .hotel(hotelId)
                .roomNumber(RoomNumber)
                .usernameClient(usernameclient)
                .checkOutDate(enddate)
                .checkInDate(startdate)
                .build();
    }

}
