package com.twocheckout.exception;
import lombok.Getter;

@Getter
public class TwocheckoutException extends Exception {
    private static final long serialVersionUID = 2L;

    private int httpStatus;

    public TwocheckoutException(String message, int httpStatus, Throwable e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

}
