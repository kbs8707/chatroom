package com.kbs8707.chatroom.chatroom.entity;

import com.google.gson.Gson;

import java.io.Serializable;

//Entity for the system
public class SystemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String message;
    private String userList;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }
}
