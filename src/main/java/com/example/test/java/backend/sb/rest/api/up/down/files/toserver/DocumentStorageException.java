package com.example.test.java.backend.sb.rest.api.up.down.files.toserver;

public class DocumentStorageException extends RuntimeException {

    public DocumentStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentStorageException(String message) {
        super((message));
    }
}
