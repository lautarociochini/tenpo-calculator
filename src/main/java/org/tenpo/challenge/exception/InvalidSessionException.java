package org.tenpo.challenge.exception;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException(final String msg) {
        super(msg);
    }

}