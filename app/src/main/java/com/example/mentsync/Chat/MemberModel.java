package com.example.mentsync.Chat;

public class MemberModel {
    private String uid;
    private boolean isSelected;

    public MemberModel(String uid) {
        this.uid = uid;
        this.isSelected = true; // By default, followers are selected
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
