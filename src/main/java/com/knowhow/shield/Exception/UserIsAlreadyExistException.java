package com.knowhow.shield.Exception;

public class UserIsAlreadyExistException extends RuntimeException {

    public UserIsAlreadyExistException(String email, Throwable throwable) {
        super(String.format("this email %s is already exist in the system", email), throwable);
    }
}
