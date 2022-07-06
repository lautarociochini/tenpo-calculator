package org.tenpo.challenge.validation;

public interface Validator<REQ> {

    boolean isValid(REQ request);
}
