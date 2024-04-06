package com.criteo.exceptions;

import com.criteo.constants.ResponseCode;
import com.criteo.models.out.ValidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FailedToCreateException.class)
    public ValidResponse handleFailedToCreateExceptions(FailedToCreateException ex) {

        log.error("FailedToCreateException: ", ex);
        return ValidResponse.of("Creation failed." +
                "Please try again later or call support.", ResponseCode.FAILED_TO_CREATE);
    }

    @ExceptionHandler(value = Exception.class)
    public ValidResponse handleExceptions(Exception ex) {

        log.error("Exception: {}, Message: {}", ex, ex.getMessage());
        return ValidResponse.ofError("Ops.. something went wrong." +
                "Please try again later or call support.");
    }

}
