package com.example.mentsync.AfterLogin;

import android.provider.SearchRecentSuggestions;

public class SearchUserItemModel {
    String profilepic;
    String name;

    SearchUserItemModel(String profilepic,String name)
    {
        this.profilepic=profilepic;
        this.name=name;
    }
}
