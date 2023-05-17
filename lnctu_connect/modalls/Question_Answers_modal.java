package com.example.lnctu_connect.modalls;

public class Question_Answers_modal {

    private String Answer;
    private String Answer_time;
    private String answer_name;
    private String image;
    private String pushkey;
    private String question_key;
    long upvotes, downvotes;
    private String Scholar_id;

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getAnswer_time() {
        return Answer_time;
    }

    public void setAnswer_time(String answer_time) {
        Answer_time = answer_time;
    }

    public String getAnswer_name() {
        return answer_name;
    }

    public void setAnswer_name(String answer_name) {
        this.answer_name = answer_name;
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

    public String getQuestion_key() {
        return question_key;
    }

    public void setQuestion_key(String question_key) {
        this.question_key = question_key;
    }

    public long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(long upvotes) {
        this.upvotes = upvotes;
    }

    public long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(long downvotes) {
        this.downvotes = downvotes;
    }

    public String getScholar_id() {
        return Scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        Scholar_id = scholar_id;
    }

    public Question_Answers_modal(String answer, String answer_time, String answer_name, String image, String pushkey, String question_key, long upvotes, long downvotes, String scholar_id) {
        Answer = answer;
        Answer_time = answer_time;
        this.answer_name = answer_name;
        this.image = image;
        this.pushkey = pushkey;
        this.question_key = question_key;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        Scholar_id = scholar_id;
    }

    public Question_Answers_modal() {
    }
}
