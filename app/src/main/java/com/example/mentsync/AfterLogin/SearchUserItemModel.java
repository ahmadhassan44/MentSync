package com.example.mentsync.AfterLogin;

public class SearchUserItemModel {
    private String profilepic;
    private String name;

    public SearchUserItemModel() {
        // Default constructor required for Firebase serialization
    }

    public SearchUserItemModel(String profilepic, String name) {
        this.profilepic = profilepic;
        this.name = name;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
