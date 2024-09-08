package com.hrp.reservation.microservices.reservation.infrastructure.outputadapters.restapi;

import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.ChangeRoomState;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.CheckRoomPriceOutputPort;
import com.hrp.reservation.microservices.reservation.infrastructure.outputports.restapi.CheckUserExistsOutputPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ReservationRestApiOutputAdapters implements CheckRoomPriceOutputPort, ChangeRoomState, CheckUserExistsOutputPort {
    private static final String URL_HOTELS = "http://localhost:8082/v1/hotels/";
    private static final String URL_USERS = "http://localhost:8081/v1/users/";
    /*
    private static final String URL_HOTELS = "http://hotel-microservice:8080/v1/hotels/";
    private static final String URL_USERS = "http://user-microservice:8080/v1/users/";
     */
    @Override
    public boolean existsByHotel(String hotelId, String roomNumber) {
        RestClient restClient = RestClient.create();
        try {
            restClient.head()
                    .uri(URL_HOTELS+"existing-room?hotelid="+hotelId+"&room-number="+roomNumber)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientResponseException e) {
            if(e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
                return false;
            } else {
                //handle trow if not already there
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public double getPriceRoom(String hotelId, String roomNumber) {
        RestClient restClient = RestClient.create();
        try{
            String url = URL_HOTELS + hotelId + "/rooms/" + roomNumber + "/price";
            ResponseEntity<Double> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(Double.class); // Send a GET request and map response to Double
            return response.getBody() != null ? response.getBody() : 0.0;
        }catch (RestClientResponseException e) {
            throw new IllegalArgumentException("something wrong getting the price per night for the room");
        }
    }

    @Override
    public boolean updateAvailable(String hotelId, String roomNumber, boolean available) {
        RestClient restClient = RestClient.create();
        try{
            String url = URL_HOTELS + hotelId + "/rooms/" + roomNumber + "/available/" + available ;
            ResponseEntity<Boolean> response = restClient.put()
                    .uri(url)
                    .retrieve()
                    .toEntity(Boolean.class); // Send a PUT request and map response to Boolean
            return response.getBody() != null ? response.getBody() : false;
        }catch (RestClientResponseException e) {
            throw new IllegalArgumentException("something wrong change the available room status for the hotel");
        }
    }

    @Override
    public boolean checkUserExists(String username) {
        RestClient restClient = RestClient.create();
        String url = URL_USERS + "exists/client?username="+username;
        try {
            restClient.head()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientResponseException e) {
            if(e.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
                return false;
            } else {
                //handle trow if not already there
                e.printStackTrace();
            }
        }
        return false;
    }
}
