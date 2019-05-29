package com.knowhow.shield.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super(String.format("This User: %s is not exist", id));
    }
}
