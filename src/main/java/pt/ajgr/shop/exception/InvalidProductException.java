package pt.ajgr.shop.exception;

import lombok.Getter;

@Getter
public class InvalidProductException extends RuntimeException {

    private final String message;
    private final int errorCode;

    public InvalidProductException(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
