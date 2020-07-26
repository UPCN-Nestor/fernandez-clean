package com.ofv.ofv.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class VerificacionException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public VerificacionException() {
        super(ErrorConstants.INVALID_PASSWORD_TYPE, "DNI Inválido", Status.BAD_REQUEST);
    }
}
