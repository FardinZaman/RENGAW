package com.example.RENGAW.exception;

public class TeamNotReadyException extends RuntimeException{

    public TeamNotReadyException() {
        super();
    }

    public TeamNotReadyException(String message) {
        super(message);
    }
}
