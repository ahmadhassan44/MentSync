package com.example.mentsync.AfterLogin;

public class SearchUserItemModel {
    private String profilepic;
    private String name;
    private String user_id; // Add this field

    public SearchUserItemModel() {
        // Default constructor required for Firebase serialization
    }

    public SearchUserItemModel(String profilepic, String name, String user_id) {
        this.profilepic = profilepic;
        this.name = name;
        this.user_id = user_id; // Ensure this is set
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

    public String getUser_id() {
        return user_id; // Ensure getter is available
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
