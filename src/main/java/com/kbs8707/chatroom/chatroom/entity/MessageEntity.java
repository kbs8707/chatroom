package com.kbs8707.chatroom.chatroom.entity;

import java.io.Serializable;

//Entity for messages
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String message;

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
