package com.java.crudApplicationMaven.payload.response;

public class BaseResponse {
    private int code;

    private String desc;

    private Object data;

    public String getDesc() {
        return null;
    }

    public Object getData() {
        return null;
    }

    public int getCode() {
        return 0;
    }

    public void setCode(int code) {
    }

    public void setDesc(String desc) {
    }

    public void setData(Object data) {
    }

    public boolean equals(Object o) {
        return false;
    }

    protected boolean canEqual(Object other) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return null;
    }

    public BaseResponse(int code, String desc, Object data) {
    }

    public BaseResponse() {
    }
}
