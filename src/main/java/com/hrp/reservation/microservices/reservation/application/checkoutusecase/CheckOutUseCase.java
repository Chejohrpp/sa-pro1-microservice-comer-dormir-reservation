package com.hrp.reservation.microservices.reservation.application.checkoutusecase;

import com.hrp.reservation.microservices.common.annotation.UseCase;
import com.hrp.reservation.microservices.payment.application.paymentchekoutusecase.PaymentCheckOutRequest;
import com.hrp.reservation.microservices.payment.domain.Payment;
import com.hrp.reservation.microservices.payment.infraestructure.inputports.PaymentInputport;
import com.hrp.reservation.microservices.payment.infraestructure.outputports.PaymentOutputPort;
import com.hrp.reservation.microservices.reservation.domain.Reservation;
import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import com.hrp.reservation.microservices.reservation.infrastructure.inputports.CheckOutInputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.CheckOutOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.db.ReservationClientOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.ChangeRoomState;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@UseCase
@Transactional
public class CheckOutUseCase implements CheckOutInputPort {
    private final CheckOutOutputPort checkOutOutputPort;
    private final ChangeRoomState changeRoomState;
    private final ReservationClientOutputPort reservationClientOutputPort;
    private final PaymentInputport paymentInputport;

    public CheckOutUseCase(CheckOutOutputPort checkOutOutputPort, ChangeRoomState changeRoomState, ReservationClientOutputPort reservationClientOutputPort, PaymentInputport paymentInputport) {
        this.checkOutOutputPort = checkOutOutputPort;
        this.changeRoomState = changeRoomState;
        this.reservationClientOutputPort = reservationClientOutputPort;
        this.paymentInputport = paymentInputport;
    }

    @Override
    public Reservation checkOutClient(long reservationId, CheckOutRequest checkOutRequest) throws IllegalArgumentException, EntityNotFoundException {
        //validate the reservation if exists
        Reservation reservation = checkOutOutputPort.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("reservation not found"));

        //verify the reservation is status reserved
        if(!reservation.getStatus().equals(ReservationStatus.RESERVED)) {
            throw new IllegalArgumentException("reservation is not reserved");
        }

        //verify if the payment methos are complete
        if(reservation.getTotalAmount() > getTotalAmout(checkOutRequest.getPayments())){
            throw new IllegalArgumentException("the total amount is insufficient");
        }
        // save the payments use case
        for (PaymentCheckOutRequest payment : checkOutRequest.getPayments()) {
            savePayments(reservation, payment);
        }

        //change the room state
        if (!changeRoomState.updateAvailable(reservation.getHotel(), reservation.getRoomNumber(),true)){
            throw new IllegalArgumentException("change room state not successfully change state");
        }

        //change the status
        reservation.setStatus(ReservationStatus.FINISHED);

        //update reservation
        reservation = reservationClientOutputPort.save(reservation);

        //return the reservation
        return reservation;
    }

    private Double getTotalAmout(List<PaymentCheckOutRequest> payments){
        return payments.stream()
                .mapToDouble(PaymentCheckOutRequest::getAmount)
                .sum();
    }

    private void savePayments(Reservation reservation, PaymentCheckOutRequest paymentCheckOutRequest){
        paymentInputport.savePayment(reservation, paymentCheckOutRequest);
    }

}
