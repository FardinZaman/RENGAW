package com.example.RENGAW.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException validationException){
        return validationException.getMessage();
    }

    @ExceptionHandler(TeamNotReadyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleTeamNotReadyException(TeamNotReadyException teamNotReadyException){
        return teamNotReadyException.getMessage();
    }

    @ExceptionHandler(TeamFullException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handleTeamFullException(TeamFullException teamFullException){
        return teamFullException.getMessage();
    }

    @ExceptionHandler(PersonnelAlreadyAssignedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handlePersonnelAlreadyAssignedException(PersonnelAlreadyAssignedException personnelAlreadyAssignedException){
        return personnelAlreadyAssignedException.getMessage();
    }

    @ExceptionHandler(DateNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handleDateNotValidException(DateNotValidException dateNotValidException){
        return dateNotValidException.getMessage();
    }

    @ExceptionHandler(SerialNotUniqueException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handleSerialNotUniqueException(SerialNotUniqueException serialNotUniqueException){
        return serialNotUniqueException.getMessage();
    }
}
