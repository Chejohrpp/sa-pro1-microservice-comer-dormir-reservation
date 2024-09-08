package com.hrp.reservation.microservices.reservation.infrastructure.inputadapters.restapi;

import com.hrp.reservation.microservices.reservation.application.checkoutusecase.CheckOutRequest;
import com.hrp.reservation.microservices.reservation.application.reservationclientusecase.ReservationClientRequest;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.CheckInInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.CheckOutInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.ReservationClientInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/reservations")
public class ReservationController {
    private final ReservationClientInputPort reservationClientInputPort;
    private final CheckInInputPort checkInInputPort;
    private final CheckOutInputPort checkOutInputPort;

    @Autowired
    public ReservationController(ReservationClientInputPort reservationClientInputPort, CheckInInputPort checkInInputPort, CheckOutInputPort checkOutInputPort) {
        this.reservationClientInputPort = reservationClientInputPort;
        this.checkInInputPort = checkInInputPort;
        this.checkOutInputPort = checkOutInputPort;
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

    @GetMapping("/check-in/{reservationId}")
    public ResponseEntity<ReservationClientResponse> checkInclient
            (@PathVariable Long reservationId) throws Exception {
        checkInInputPort.checkInclient(reservationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReservationClientResponse.from("the check in was successful completely, enjoy"));
    }

    @PostMapping("/check-out/{reservationId}")
    public ResponseEntity<ReservationClientResponse> checkOutClient
            (@PathVariable Long reservationId,
             @RequestBody CheckOutRequest checkOutRequest) throws Exception {
        checkOutInputPort.checkOutClient(reservationId, checkOutRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReservationClientResponse.from("the check OUT was successful completely, come back"));
    }
}
