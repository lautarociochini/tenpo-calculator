package org.tenpo.challenge.exception;

public class DuplicatedUserException extends RuntimeException {

    public DuplicatedUserException(final String msg) {
        super(msg);
    }

}