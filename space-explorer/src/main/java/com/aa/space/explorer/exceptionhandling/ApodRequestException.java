package com.aa.space.explorer.exceptionhandling;

public class ApodRequestException extends RuntimeException {
    public ApodRequestException() {
    }

    public ApodRequestException(String message) {
        super(message);
    }

    public ApodRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApodRequestException(Throwable cause) {
        super(cause);
    }

    public ApodRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
