package com.module5.AuthorAndBookManagement.exception;

public class AuthorNotFoundException
        extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
