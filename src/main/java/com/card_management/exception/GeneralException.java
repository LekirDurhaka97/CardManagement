package com.card_management.exception;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

@Getter
public class GeneralException extends AbstractThrowableProblem {
    public GeneralException(String param) {
        super(URI.create("http://localhost:8083/api"), param, Status.BAD_REQUEST);
    }
}