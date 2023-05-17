package com.example.lnctu_connect.modalls;

public class follow_list {

    private  String uniq_name;

    public String getUniq_name() {
        return uniq_name;
    }

    public void setUniq_name(String uniq_name) {
        this.uniq_name = uniq_name;
    }

    public String getScholar_id() {
        return Scholar_id;
    }

    public void setScholar_id(String scholar_id) {
        Scholar_id = scholar_id;
    }

    public String getUser__name() {
        return user__name;
    }

    public void setUser__name(String user__name) {
        this.user__name = user__name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private  String Scholar_id;

    public follow_list() {
    }

    private  String user__name;
    private String image;

    public follow_list(String uniq_name, String scholar_id, String user__name, String image) {
        this.uniq_name = uniq_name;
        Scholar_id = scholar_id;
        this.user__name = user__name;
        this.image = image;
    }
}
