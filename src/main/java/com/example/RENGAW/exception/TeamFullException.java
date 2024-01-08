package com.example.RENGAW.exception;

public class TeamFullException extends RuntimeException{

    public TeamFullException(){
        super();
    }

    public TeamFullException(String message){
        super(message);
    }
}
