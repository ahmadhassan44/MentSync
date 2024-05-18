package com.example.mentsync.AfterLogin.Feed;

public class ImagePostModel {
    public String uid;
    public String date;
    public String caption;
    public String imgurl;
    public Long likes;

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

    public Long getLikecount() {
        return likes;
    }

    public void setLikecount(Long likes) {
        this.likes = likes;
    }
    public String toString() {
        return "ImagePostModel{" +
                "uid"+uid+
                ", date='" + date + '\'' +
                ", caption='" + caption + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", likecount='" + likes + '\'' +
                '}';
    }
}
