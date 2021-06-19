package com.muvlin.app.apiclient.pojo;

public class LoginRequest {
    private String user;
    private String pass;
    private String origin;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LoginRequest(String user, String pass, String origin) {
        this.user = user;
        this.pass = pass;
        this.origin = origin;
    }

    public LoginRequest() {
    }
}
