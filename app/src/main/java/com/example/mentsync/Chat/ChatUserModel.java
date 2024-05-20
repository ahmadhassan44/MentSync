package com.example.mentsync.Chat;

public class ChatUserModel {
    private String uid;
    ChatUserModel(){}
    ChatUserModel(String uid)
    {
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
