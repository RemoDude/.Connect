package com.example.lnctu_connect.modalls;

public class rough_two {


    public rough_two(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private  long timestamp;

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public rough_two(String pushkey) {
        this.pushkey = pushkey;
    }

    private String pushkey;

    public rough_two() {
    }

    private String Scholar_id;

    public String getScholar_id() {
        return Scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        Scholar_id = scholar_id;
    }

    public String getComment_reply() {
        return comment_reply;
    }

    public void setComment_reply(String comment_reply) {
        this.comment_reply = comment_reply;
    }

    public rough_two(String scholar_id, String comment_reply) {
        Scholar_id = scholar_id;
        this.comment_reply = comment_reply;
    }

    private String comment_reply;
}
