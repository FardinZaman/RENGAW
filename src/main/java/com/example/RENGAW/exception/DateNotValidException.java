package com.example.RENGAW.exception;

public class DateNotValidException extends RuntimeException{

    public DateNotValidException(){
        super();
    }

    public DateNotValidException(String message){
        super(message);
    }
}
