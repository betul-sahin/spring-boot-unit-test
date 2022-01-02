package com.betulsahin.springbootunittest.exceptions;

public class IdentificationNumberNotValidException extends RuntimeException{
    public IdentificationNumberNotValidException(String message){
        super(message);
    }
}
