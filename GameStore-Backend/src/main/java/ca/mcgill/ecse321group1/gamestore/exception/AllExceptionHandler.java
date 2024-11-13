package ca.mcgill.ecse321group1.gamestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ca.mcgill.ecse321group1.gamestore.dto.ErrorDto;

@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = e.getMessage();

        ErrorDto responseBody = new ErrorDto(errorMessage);
        return new ResponseEntity<ErrorDto>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
