package com.example.mentsync.AfterLogin.Feed;

public class AnswerModel {
    private String uid;
    private String answer;
    String queryId;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public AnswerModel() {
        // Default constructor required for calls to DataSnapshot.getValue(AnswerModel.class)
    }

    public AnswerModel(String uid, String answer,String queryId) {
        this.uid = uid;
        this.answer = answer;
        this.queryId=queryId;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
