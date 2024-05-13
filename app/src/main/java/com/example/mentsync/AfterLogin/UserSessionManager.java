package com.example.mentsync.AfterLogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSessionManager {

    String prefname="user_data";
    static SharedPreferences pref;
    Context context;
    DataSnapshot snapshot;
    public UserSessionManager(Context context,DataSnapshot snapshot)
    {
        this.context=context;
        this.pref=context.getSharedPreferences(prefname,MODE_PRIVATE);
        this.snapshot=snapshot;
    }
    public UserSessionManager(Context context)
    {
        this.context=context;
        this.pref=context.getSharedPreferences(prefname,MODE_PRIVATE);
    }

    public  void endSession()
    {
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.apply();
    }
    public void startSession() {
        insertCurrentUserDataIntoSharedPreferences(snapshot);
    }
    private static void insertCurrentUserDataIntoSharedPreferences(DataSnapshot snapshot){
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("loggedin?",true);
        editor.putString("name",snapshot.child("name").getValue(String.class));
        editor.putString("cms",snapshot.child("cms").getValue(String.class));
        editor.putString("email",snapshot.child("email").getValue(String.class));
        editor.putString("role",snapshot.child("role").getValue(String.class));
        editor.putString("profile_pic",snapshot.child("profile_pic").getValue(String.class));
        editor.apply();
    }
}
