package com.example.mc_project_srijan.model;

public class user_messages {
    String userId;
    String msg,msgID;
    public user_messages() {

    }

    public user_messages(String userId, String msg) {
        this.userId = userId;
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }
}
