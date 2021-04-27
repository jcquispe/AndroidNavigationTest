package com.muvlin.app.apiclient.pojo;

public class LoginResponse {
    public String token_type;
    public int expires_in;
    public String access_token;
    public String refresh_token;
    public User user;

    public class User{
        public int id;
        public String name;
        public String email;
        public Object email_verified_at;
        public String created_at;
        public String updated_at;
    }
}
