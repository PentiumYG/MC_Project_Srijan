package com.example.mc_project_srijan.model;

public class user {
    String profile_pic,name,mail,pass,userId,lastMessage,status;
    public user(){

    }

    public user(String profile_pic, String name, String mail, String pass, String userId, String lastMessage, String status) {
        this.profile_pic = profile_pic;
        this.name = name;
        this.mail = mail;
        this.pass = pass;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    public user(String name, String mail, String pass) {
        this.name = name;
        this.mail = mail;
        this.pass = pass;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
