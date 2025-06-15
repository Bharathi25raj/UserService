package com.bharathi.userservice.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message){
        super(message);
    }
}
