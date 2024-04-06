package com.criteo.constants;

public enum ResponseCode {

    OK(0),
    ERROR(-10_000, "ERROR"),
    NOT_FOUND(-12_002, "NOT_FOUND"),
    BAD_REQUEST(-12_006, "BAD_REQUEST"),
    INVALID_VALUE(-12_005, "INVALID_VALUE"),
    ALREADY_EXISTS(-12_007, "ALREADY_EXISTS"),
    FAILED_TO_CREATE(-12_001, "FAILED_TO_CREATE"),
    AUTHORIZATION_FAILED(-11_001, "AUTHORIZATION_FAILED"),
    AUTHENTICATION_FAILED(-11_000, "AUTHENTICATION_FAILED");

    int code;
    String message;

    ResponseCode(int code) {
        this.code = code;
        this.message = name();
    }

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
