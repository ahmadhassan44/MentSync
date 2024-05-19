package com.example.mentsync.AfterLogin.Feed;

import androidx.annotation.NonNull;

public class QueryPostModel {
    public String date;
    public String Text;
    public  String uid;
    String queryId;

    public QueryPostModel() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
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
                "queryId" + queryId+
                "uid='" + uid + '\'' +
                ", query_body='" + Text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
