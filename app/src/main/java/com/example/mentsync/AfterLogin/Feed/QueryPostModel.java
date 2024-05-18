package com.example.mentsync.AfterLogin.Feed;

import androidx.annotation.NonNull;

public class QueryPostModel {
    public String date;
    public String Text;
    public  String uid;

    public QueryPostModel() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuery_body() {
        return Text;
    }

    public void setQuery_body(String query_body) {
        this.Text = query_body;
    }
    @NonNull
    public String toString() {
        return "QueryPostModel{" +
                "uid='" + uid + '\'' +
                ", query_body='" + Text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
