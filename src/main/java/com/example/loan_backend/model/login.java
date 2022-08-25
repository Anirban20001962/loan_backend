package com.example.loan_backend.model;

public class login {
    private String mailid;
    private String password;
    public login(String mailid,String password){
        this.mailid = mailid;
        this.password = password;
    }
    public String getMailid() {
        return mailid;
    }
    public void setMailid(String mailid) {
        this.mailid = mailid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
