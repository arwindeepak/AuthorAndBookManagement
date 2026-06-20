package com.module5.AuthorAndBookManagement.exception;

public class BookNotFoundException
        extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}