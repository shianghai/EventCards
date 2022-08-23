package com.assassin.timetableapp;

public class Today {
    int _id;
    String course_name;
    String venue;
    String time;
    String day;

    public Today(){}

    public Today(int id, String courseName, String venue, String time, String day){
        this.course_name = courseName;
        this._id = id;
        this.venue = venue;
        this.time = time;
        this.day = day;
    }

    public int get_id() {
        return _id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getDay() {
        return day;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
