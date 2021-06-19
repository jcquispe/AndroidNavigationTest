package com.muvlin.app.apiclient.pojo;

public class BasicResponse {
    private Boolean success;
    private Object message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public BasicResponse(Boolean success, Object message) {
        this.success = success;
        this.message = message;
    }

    public BasicResponse(Boolean success) {
        this.success = success;
    }
}
