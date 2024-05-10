package com.example.mentsync.AfterLogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

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
    public void startSession(Context context,FirebaseUser user) {
        insertCurrentUserDataIntoSharedPreferences(user);
    }
    private void insertCurrentUserDataIntoSharedPreferences(FirebaseUser user) {

    }
}
