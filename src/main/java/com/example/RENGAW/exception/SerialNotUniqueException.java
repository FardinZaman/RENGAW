package com.example.RENGAW.exception;

public class SerialNotUniqueException extends RuntimeException{

    public SerialNotUniqueException() {
        super();
    }

    public SerialNotUniqueException(String message) {
        super(message);
    }
}
