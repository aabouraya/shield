package com.knowhow.shield.controller.err;

import com.knowhow.shield.Exception.UserIsAlreadyExistException;
import com.knowhow.shield.Exception.UserNotFoundException;
import com.knowhow.shield.Exception.VerificationTokeIsNotValidException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
class IamControllerAdvice {

    @ExceptionHandler(UserIsAlreadyExistException.class)
    public ResponseEntity<String> userIsAlreadyException(UserIsAlreadyExistException exp) {
        return error(exp, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameIsNotFoundException(UsernameNotFoundException exp) {
        return error(exp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VerificationTokeIsNotValidException.class)
    public ResponseEntity<String> verificationTokeException(VerificationTokeIsNotValidException exp) {
        return error(exp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException exp) {
        return error(exp, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> error(Exception exception, HttpStatus httpStatus) {
        String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        log.error(message, exception);
        return new ResponseEntity(message, httpStatus);
    }
}