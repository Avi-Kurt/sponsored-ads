package com.criteo.models.out;


import com.criteo.constants.ResponseCode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ValidResponse<T> {

    @Getter
    private final T payload;

    @NotNull
    private final int code;

    private ValidResponse(T payload, ResponseCode code) {
        this.payload = payload;
        if (code == null) {
            throw new RuntimeException("ResponseCode can't be null");
        }
        this.code = code.getCode();
    }

    public static <T> ValidResponse<T> of(T object) {
        return new ValidResponse(object, ResponseCode.OK);
    }

    public static ValidResponse<String> ofOK() {
        return of("OK");
    }

    public static ValidResponse ofError(ResponseCode code) {
        return new ValidResponse(code.getCode(), code);
    }

    public static ValidResponse ofError(String message) {
        return of(message, ResponseCode.ERROR);
    }

    public static ValidResponse of(String customMessage, ResponseCode code) {
        return new ValidResponse(customMessage, code);
    }

    public Integer getCode() {
        return this.code;
    }

}
