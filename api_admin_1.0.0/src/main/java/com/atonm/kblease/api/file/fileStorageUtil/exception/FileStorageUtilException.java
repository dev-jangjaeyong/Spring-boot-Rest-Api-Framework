package com.atonm.kblease.api.file.fileStorageUtil.exception;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
public class FileStorageUtilException extends RuntimeException {
    public FileStorageUtilException(String message) {
        super(message);
    }

    public FileStorageUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
