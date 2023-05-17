package com.example.lnctu_connect.modalls;

public class qna_data {

//    public  String profile_qna_image;
    public  String user_qna_name_asked;
    public  String qna_timestamp_asked;
    public  String category_qna_asked;
    public  String question_qna_asked;
    public  String pushkey;
    public  String image;
    public  Long upvote;
    public  Long downvote;
    public  String scholar_id;


    public String getUser_qna_name_asked() {
        return user_qna_name_asked;
    }

    public void setUser_qna_name_asked(String user_qna_name_asked) {
        this.user_qna_name_asked = user_qna_name_asked;
    }

    public String getQna_timestamp_asked() {
        return qna_timestamp_asked;
    }

    public void setQna_timestamp_asked(String qna_timestamp_asked) {
        this.qna_timestamp_asked = qna_timestamp_asked;
    }

    public String getCategory_qna_asked() {
        return category_qna_asked;
    }

    public void setCategory_qna_asked(String category_qna_asked) {
        this.category_qna_asked = category_qna_asked;
    }

    public String getQuestion_qna_asked() {
        return question_qna_asked;
    }

    public void setQuestion_qna_asked(String question_qna_asked) {
        this.question_qna_asked = question_qna_asked;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUpvote() {
        return upvote;
    }

    public void setUpvote(Long upvote) {
        this.upvote = upvote;
    }

    public Long getDownvote() {
        return downvote;
    }

    public void setDownvote(Long downvote) {
        this.downvote = downvote;
    }

    public String getScholar_id() {
        return scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        this.scholar_id = scholar_id;
    }

    public qna_data() {
    }



    public qna_data(String user_qna_name_asked, String qna_timestamp_asked, String category_qna_asked, String question_qna_asked, String pushkey, String image, Long upvote, Long downvote, String scholar_id) {
        this.user_qna_name_asked = user_qna_name_asked;
        this.qna_timestamp_asked = qna_timestamp_asked;
        this.category_qna_asked = category_qna_asked;
        this.question_qna_asked = question_qna_asked;
        this.pushkey = pushkey;
        this.image = image;
        this.upvote = upvote;
        this.downvote = downvote;
        this.scholar_id = scholar_id;
    }

    //
//    public String getProfile_url() {
//        return profile_url;
//    }
//
//    public void setProfile_url(String profile_url) {
//        this.profile_url = profile_url;
//    }
}


