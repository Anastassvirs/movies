package com.example.sbertestmovie.exception;

public class WrongParametersException extends RuntimeException {
    public WrongParametersException(final String message) {
        super(message);
    }
}