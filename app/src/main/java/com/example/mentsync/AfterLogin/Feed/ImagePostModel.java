package com.example.mentsync.AfterLogin.Feed;

public class ImagePostModel {
    public String uid;
    public String date;
    public String caption;
    public String imgurl;
    public String postId;

    public ImagePostModel() {
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String imgurl() {
        return imgurl;
    }

    public void imgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String toString() {
        return "ImagePostModel{" +
                "postID:"+postId+
                "uid"+uid+
                ", date='" + date + '\'' +
                ", caption='" + caption + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
