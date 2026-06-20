package com.module5.AuthorAndBookManagement.exception;

public class DuplicateAuthorException
        extends RuntimeException {

    public DuplicateAuthorException(String message) {
        super(message);
    }
}