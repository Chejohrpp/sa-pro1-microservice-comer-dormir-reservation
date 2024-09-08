package com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi;

public interface CheckUserExistsOutputPort {
    boolean checkUserExists(String username);
}
