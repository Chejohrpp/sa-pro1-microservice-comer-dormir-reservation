package com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi;

public interface ChangeRoomState {
    boolean updateAvailable(String hotelId, String roomNumber,boolean available);
}
