package com.example.lnctu_connect.Adapters;
//

public class MyData {

    private String Answer;

    public MyData(String answer, String scholar_id) {
        Answer = answer;
        Scholar_id = scholar_id;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getScholar_id() {
        return Scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        Scholar_id = scholar_id;
    }

    private String Scholar_id;
}



//public class MyData {
//    private String answersTime;
//    private String image;
//
//    public void setAnswersTime(String answersTime) {
//        this.answersTime = answersTime;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public MyData(String answersTime, String image) {
//        this.answersTime = answersTime;
//        this.image = image;
//    }
//
//    public String getAnswersTime() {
//        return answersTime;
//    }
//
//    public String getImage() {
//        return image;
//    }
//}
