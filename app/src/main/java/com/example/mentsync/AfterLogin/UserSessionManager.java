package com.example.mentsync.AfterLogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSessionManager {

    String prefname="user_data";
    SharedPreferences pref;
    Context context;
    public UserSessionManager(Context context)
    {
        this.context=context;
        this.pref=context.getSharedPreferences(prefname,MODE_PRIVATE);
    }
    public void endSession()
    {
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.apply();
    }
    public void startSession(Context context, JSONObject user) throws JSONException {
        insertCurrentUserDataIntoSharedPreferences(user);
    }
    private void insertCurrentUserDataIntoSharedPreferences(JSONObject user) throws JSONException {
        pref=context.getSharedPreferences(prefname,MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("u_id",user.optInt("u_id"));
        editor.putString("name",user.optString("name"));
        editor.putString("email",user.getString("email"));
        editor.putString("profile_pic",user.optString("profile_pic"));
        editor.putBoolean("loggedin?",true);
        editor.apply();
    }
}
