package com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.restapi;

import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.CheckRoomPriceOutputPort;
import org.springframework.stereotype.Component;

@Component
public class ReservationRestApiOutputAdapters implements CheckRoomPriceOutputPort {
    @Override
    public boolean existsByHotel(String hotelId, String RoomNumber) {
        return true;
    }

    @Override
    public double getPriceRoom(String hotelId, String RoomNumber) {
        return 10;
    }
}
