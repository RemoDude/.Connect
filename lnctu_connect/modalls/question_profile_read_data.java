package com.example.lnctu_connect.modalls;

public class question_profile_read_data {

    public String image, category_qna_asked, question_qna_asked;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser__name() {
        return category_qna_asked;
    }

    public void setUser__name(String user__name) {
        this.category_qna_asked = user__name;
    }

    public String getQuestion_qna_asked() {
        return question_qna_asked;
    }

    public void setQuestion_qna_asked(String question_qna_asked) {
        this.question_qna_asked = question_qna_asked;
    }

    public question_profile_read_data() {
    }

    public question_profile_read_data(String image, String user__name, String question_qna_asked) {
        this.image = image;
        this.category_qna_asked = user__name;
        this.question_qna_asked = question_qna_asked;
    }
}
