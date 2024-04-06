package com.criteo.exceptions;

import com.criteo.constants.ResponseCode;
import com.criteo.models.out.ValidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ValidResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {

        log.debug("Bad Request: {}", ex.getMessage());
        return ValidResponse.of("Bad request: " + ex.getMessage(), ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ValidResponse handleMethodValidationException(HandlerMethodValidationException ex) {

        log.debug("Bad Request: ", ex);
        return ValidResponse.of("Bad request: Please check request parameters.", ResponseCode.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToCreateException.class)
    public ValidResponse handleFailedToCreateExceptions(FailedToCreateException ex) {

        log.error("FailedToCreate: ", ex);
        return ValidResponse.of("Creation failed." +
                "Please try again later or call support.", ResponseCode.FAILED_TO_CREATE);
    }

    @ExceptionHandler(Exception.class)
    public ValidResponse handleExceptions(Exception ex) {

        log.error("Exception: ", ex);
        return ValidResponse.ofError("Ops.. something went wrong." +
                "Please try again later or call support.");
    }

}
