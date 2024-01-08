package com.example.RENGAW.exception;

public class PersonnelAlreadyAssignedException extends RuntimeException{

    public PersonnelAlreadyAssignedException(){
        super();
    }

    public PersonnelAlreadyAssignedException(String message){
        super(message);
    }
}
