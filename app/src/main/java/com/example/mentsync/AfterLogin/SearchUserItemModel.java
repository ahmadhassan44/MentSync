package com.example.mentsync.AfterLogin;

public class SearchUserItemModel {
    private String profile_pic;
    private String name;

    public SearchUserItemModel() {
        // Default constructor required for Firebase serialization
    }

    public SearchUserItemModel(String profilepic, String name) {
        this.profile_pic = profilepic;
        this.name = name;
    }

    public String getProfilepic() {
        return profile_pic;
    }

    public void setProfilepic(String profilepic) {
        this.profile_pic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
