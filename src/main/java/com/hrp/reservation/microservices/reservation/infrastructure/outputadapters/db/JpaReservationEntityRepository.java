package com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.db;

import com.hrp.reservation.microservices.reservation.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaReservationEntityRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByHotelAndRoomNumberAndStatusNot(String hotel, String roomNumber, ReservationStatus reservationStatus);
}
