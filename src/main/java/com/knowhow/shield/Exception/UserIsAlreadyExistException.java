package com.knowhow.shield.Exception;

public class UserIsAlreadyExistException extends RuntimeException {

    public UserIsAlreadyExistException(String email) {
        super(String.format("this email %s is already exist in the system", email));
    }
}
