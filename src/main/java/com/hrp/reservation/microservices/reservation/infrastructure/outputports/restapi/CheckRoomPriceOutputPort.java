package com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi;

public interface CheckRoomPriceOutputPort {
    boolean existsByHotel(String hotelId, String RoomNumber);

    double getPriceRoom(String hotelId, String RoomNumber);
}
