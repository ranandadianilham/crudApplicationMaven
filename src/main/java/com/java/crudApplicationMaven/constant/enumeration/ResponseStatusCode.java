package com.java.crudApplicationMaven.constant.enumeration;

@SuppressWarnings("all")
public enum ResponseStatusCode {

    // ----------------------------------------------------------------------------------------------------------------------
    // SUCCESS CODE
    // ----------------------------------------------------------------------------------------------------------------------

    SUCCESS(200, "Success"),

    // ----------------------------------------------------------------------------------------------------------------------
    // GENERAL ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    DATA_NOT_FOUND(404, "Not Found"),
    ;
    public static final ResponseStatusCode DEFAULT_RESPONSE_STATUS_CODE = SUCCESS;

    int code;
    String desc;

    ResponseStatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public boolean isEqual(int code) {
        return code() == code;
    }

    public static ResponseStatusCode of(int code) {
        return of(code, DEFAULT_RESPONSE_STATUS_CODE);
    }

    public static ResponseStatusCode of(int code, ResponseStatusCode defaultResponseStatusCode) {
        for (ResponseStatusCode rsc : ResponseStatusCode.values())
            if (rsc.isEqual(code))
                return rsc;
        return defaultResponseStatusCode;
    }
}
