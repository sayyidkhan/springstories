package com.main.springstories.controller.exceptions;

public class BookValidationException extends RuntimeException {
    public BookValidationException(String message) {
        super(message);
    }
}
