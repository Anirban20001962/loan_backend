package com.example.loan_backend.response;

public class MsgDataResponse {
    public String msg;
    public Object data;

    public MsgDataResponse(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
}
