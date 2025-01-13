package com.example.Tutorial.handler;

import com.example.Tutorial.exception.TutorialAlreadyExistException;
import com.example.Tutorial.exception.TutorialNotFoundByGivenID;
import com.example.Tutorial.exception.TutorialNotFoundException;
import com.example.Tutorial.exception.TutorialNotPublishedException;
import com.example.Tutorial.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(value = TutorialAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleTutorialAlreadyExistException(TutorialAlreadyExistException e) {
        log.info(" This Tutorial already added");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TutorialNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTutorialNotFoundException(TutorialNotFoundException e) {
        log.info("no such element in the Database");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TutorialNotPublishedException.class)
    public ResponseEntity<ErrorResponse> handleTutorialNotPublishedException(TutorialNotPublishedException e) {
        log.info("Tutorial isn't published yet in DB");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TutorialNotFoundByGivenID.class)
    public ResponseEntity<ErrorResponse> handleTutorialNotFindByGivenID(TutorialNotFoundByGivenID e) {
        log.info("tutorial id isn't registered in DB");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
