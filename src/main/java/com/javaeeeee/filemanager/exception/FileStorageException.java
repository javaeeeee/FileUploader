package com.javaeeeee.filemanager.exception;

/**
 * Exception occurring during attempts to save or retrieve a file.
 */
public class FileStorageException extends Exception {
    public FileStorageException() {
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }

    public FileStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
