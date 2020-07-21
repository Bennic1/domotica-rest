package nl.bennic.rest.domotica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {

        //Payload exception details
        ApiExcept apiExcept = new ApiExcept(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());

        //Response Entity
        return new ResponseEntity<>(apiExcept, HttpStatus.BAD_REQUEST);
    }
}
