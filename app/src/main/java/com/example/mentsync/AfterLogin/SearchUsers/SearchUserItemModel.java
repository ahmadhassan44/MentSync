package com.example.mentsync.AfterLogin.SearchUsers;

public class SearchUserItemModel {
    private String profile_pic;
    private String name;
    private String userid;

    public SearchUserItemModel() {
        // Default constructor required for Firebase serialization
    }

    public SearchUserItemModel(String profilepic, String name,String userid) {
        this.profile_pic = profilepic;
        this.name = name;
        this.userid=userid;
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
    public String getUid() {
        return userid;
    }
}
