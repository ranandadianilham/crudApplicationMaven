package com.java.crudApplicationMaven.constant.enumeration;

@SuppressWarnings("all")
public enum ResponseStatusCode {

    // ----------------------------------------------------------------------------------------------------------------------
    // SUCCESS CODE
    // ----------------------------------------------------------------------------------------------------------------------

    SUCCESS(0, "Success"),

    // ----------------------------------------------------------------------------------------------------------------------
    // GENERAL ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    DATA_NOT_FOUND(1001, "Your request data not found"),
    INTERNAL_SYSTEM_ERROR(1002, "Internal system error"),
    VALIDATION_ERROR(1003, "Validation system error"),

    MANDATORY_PARAMETER(1005, "Mandatory requirement is missing"),
    INVALID_FILE(1006, "Invalid File"),
    DATA_TOO_LONG(1007, "Data too long"),

    // ----------------------------------------------------------------------------------------------------------------------
    // PROTOCOL ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    NOT_AUTHORIZED(1401, "Bad Credential / Unauthorized"),
    PATH_NOT_FOUND(1404, "Path not exist"),
    CONFLICT_DATA_EXIST(1409, "Your submit data has a conflict because it already exists"),

    // ----------------------------------------------------------------------------------------------------------------------
    // AUTHENTICATION ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    AUTH_USERGROUP_DISABLE(2001, "User Account Group is Disable"),
    AUTH_USERNAME_DISABLE(2002, "User Account is Disable"),
    AUTH_EMAIL_NOT_VERIFIED(2003, "Email Account is not verified yet"),
    AUTH_MSISDN_NOT_VERIFIED(2004, "MSISDN Account is not verified yet"),
    AUTH_MSISDN_NOT_QUALIFIED(2005, "MSISDN Operator is not qualified for authorization by system"),
    AUTH_EMAIL_VERIFIED(2006, "Email Account is verified"),
    AUTH_TOKEN_EXPIRED(2007, "Jwt Token expired"),

    // ----------------------------------------------------------------------------------------------------------------------
    // ACCESS RESTRICTION ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    PERMISSION_DENIED_AND_UNAUTHORIZED(2401, "Permission Denied or Unauthorized"),

    // ----------------------------------------------------------------------------------------------------------------------
    // UNHANDLED ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------

    UNHANDLED_ERROR(9999, "Unhandled error"),

    // ----------------------------------------------------------------------------------------------------------------------
    // NETWORK ERROR CODE
    // ----------------------------------------------------------------------------------------------------------------------
    NETOWRK_CONNECTION(4004, "Netwrok error connection"),
    ; // END
    // ----------------------------------------------------------------------------------------------------------------------

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
