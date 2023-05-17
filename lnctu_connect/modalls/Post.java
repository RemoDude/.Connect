package com.example.lnctu_connect.modalls;

public class Post {
//    private String title;
//    private String body;

    private String Caption;
    private String post_image;
    private String post_time_stamp;
    private long post_mili_second;
    private String pushkey;
    private String scholar_id;
    private String user_image;
    private String user_name;
    private long  Likes;

    public long getLikes() {
        return Likes;
    }

    public void setLikes(long likes) {
        Likes = likes;
    }

    public Post(long likes) {
        Likes = likes;
    }

    public Post() {
    }

    public Post(String caption, String post_image, String post_time_stamp, long post_mili_second, String pushkey, String scholar_id, String user_image, String user_name) {
        Caption = caption;
        this.post_image = post_image;
        this.post_time_stamp = post_time_stamp;
        this.post_mili_second = post_mili_second;
        this.pushkey = pushkey;
        this.scholar_id = scholar_id;
        this.user_image = user_image;
        this.user_name = user_name;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_time_stamp() {
        return post_time_stamp;
    }

    public void setPost_time_stamp(String post_time_stamp) {
        this.post_time_stamp = post_time_stamp;
    }

    public long getPost_mili_second() {
        return post_mili_second;
    }

    public void setPost_mili_second(long post_mili_second) {
        this.post_mili_second = post_mili_second;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getScholar_id() {
        return scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        this.scholar_id = scholar_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
//    public Post(String title, String body) {
//        this.title = title;
//        this.body = body;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getBody() {
//        return body;
//    }
}
