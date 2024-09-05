package com.hrp.reservation.microservices.reservation.infrastructure.inputadapters.restapi;

import com.hrp.reservation.microservices.reservation.application.reservationclientusecase.ReservationClientRequest;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.ReservationClientInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/reservations")
public class ReservationController {
    private final ReservationClientInputPort reservationClientInputPort;

    @Autowired
    public ReservationController(ReservationClientInputPort reservationClientInputPort) {
        this.reservationClientInputPort = reservationClientInputPort;
    }

    @PostMapping("{hotelId}/{roomNumber}/client")
    public ResponseEntity<ReservationClientResponse> reservationcliente(
            @PathVariable String hotelId,
            @PathVariable String roomNumber,
            @RequestBody ReservationClientRequest reservationClientRequest) throws Exception {
        reservationClientInputPort.ReservationRoom(hotelId, roomNumber, reservationClientRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReservationClientResponse.from("bookmark the reservation is successful completely"));
    }
}
