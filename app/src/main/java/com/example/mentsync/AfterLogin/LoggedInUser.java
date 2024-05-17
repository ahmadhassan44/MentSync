package com.example.mentsync.AfterLogin;

import com.example.mentsync.Signup.NewUserData;

public class LoggedInUser {
    private static LoggedInUser instance;

    public static LoggedInUser getInstance()
    {
        if(instance==null)
        {
            instance=new LoggedInUser();
            return instance;
        }
        return instance;
    }
    private String user_id;
    private String name;
    private String CMS;
    private String email;
    private String profilePic;

    public String getName() {
        return name;
    }

    public String getCMS() {
        return CMS;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCMS(String CMS) {
        this.CMS = CMS;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
