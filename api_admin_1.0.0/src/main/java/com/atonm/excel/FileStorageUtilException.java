package com.atonm.excel;

/**
 * @author jangjaeyoung
 * @since 2019-11-20
 */
public class FileStorageUtilException extends RuntimeException {
    public FileStorageUtilException(String message) {
        super(message);
    }

    public FileStorageUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
