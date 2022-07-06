package org.tenpo.challenge.transformer;

public interface Transformer<REQ, RES> {

    RES transform(REQ request);
}