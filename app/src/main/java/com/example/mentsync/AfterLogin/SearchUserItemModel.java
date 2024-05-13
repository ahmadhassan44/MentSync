package com.example.mentsync.AfterLogin;

import android.provider.SearchRecentSuggestions;

public class SearchUserItemModel {
    String profilepic=null;
    String name=null;

    SearchUserItemModel(String profilepic,String name)
    {
        this.profilepic=profilepic;
        this.name=name;
    }
}
