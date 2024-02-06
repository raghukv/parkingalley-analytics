package com.parkingalley.parkingalleyanalytics.model;

public class ResponseDetails {
    private int code;        // 0: fail; 1: success
    private String msg;      // Return message (when code = 0)

    // Constructor
    public ResponseDetails(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // Optionally, consider overriding toString(), equals(), and hashCode() methods.
    // Example:
    @Override
    public String toString() {
        return "ResponseDetails{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
