package com.javaeeeee.filemanager.exception;

/**
 * Exception thorown when a file is not found in a storage.
 */
public class FileNotFoundInStorageException extends Exception {
    public FileNotFoundInStorageException() {
    }

    public FileNotFoundInStorageException(String message) {
        super(message);
    }

    public FileNotFoundInStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundInStorageException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundInStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
