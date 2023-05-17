package com.example.lnctu_connect.modalls;

public class My_Question_Modal {

//
//    username = snapshot1.child("user_qna_name_asked").getValue(String.class);
//    pofile_url = snapshot1.child("image").getValue(String.class);
//    push__key = snapshot1.child("pushkey").getValue(String.class);
//    category = snapshot1.child("category_qna_asked").getValue(String.class);
//
//    upvote = snapshot1.child("upvote").getValue(Integer.class);
//    downvote = snapshot1.child("downvote").getValue(Integer.class);
//


    public String question_qna_asked, qna_timestamp_asked, user_qna_name_asked, image, pushkey, category_qna_asked,
            scholar_id;
    long upvote, downvote;

    public String getScholar_id() {
        return scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        this.scholar_id = scholar_id;
    }

    public My_Question_Modal(String scholar_id) {
        this.scholar_id = scholar_id;
    }

    public String getQuestion_qna_asked() {
        return question_qna_asked;
    }

    public void setQuestion_qna_asked(String question_qna_asked) {
        this.question_qna_asked = question_qna_asked;
    }

    public String getQna_timestamp_asked() {
        return qna_timestamp_asked;
    }

    public void setQna_timestamp_asked(String qna_timestamp_asked) {
        this.qna_timestamp_asked = qna_timestamp_asked;
    }

    public String getUser_qna_name_asked() {
        return user_qna_name_asked;
    }

    public void setUser_qna_name_asked(String user_qna_name_asked) {
        this.user_qna_name_asked = user_qna_name_asked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getCategory_qna_asked() {
        return category_qna_asked;
    }

    public void setCategory_qna_asked(String category_qna_asked) {
        this.category_qna_asked = category_qna_asked;
    }

    public long getUpvote() {
        return upvote;
    }

    public void setUpvote(long upvote) {
        this.upvote = upvote;
    }

    public long getDownvote() {
        return downvote;
    }

    public void setDownvote(long downvote) {
        this.downvote = downvote;
    }

    public My_Question_Modal(String question_qna_asked, String qna_timestamp_asked, String user_qna_name_asked, String image, String pushkey, String category_qna_asked, long upvote, long downvote) {

        this.question_qna_asked = question_qna_asked;
        this.qna_timestamp_asked = qna_timestamp_asked;
        this.user_qna_name_asked = user_qna_name_asked;
        this.image = image;
        this.pushkey = pushkey;
        this.category_qna_asked = category_qna_asked;
        this.upvote = upvote;
        this.downvote = downvote;
    }

    public My_Question_Modal() {

    }
}
