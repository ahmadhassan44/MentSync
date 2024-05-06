package com.example.mentsync.AfterLogin;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {

    String prefname="user_data";
    SharedPreferences pref;
    UserSessionManager(Context context)
    {
        pref=context.getSharedPreferences(prefname,Context.MODE_PRIVATE);
    }
    void endSession()
    {
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.apply();
    }
}
