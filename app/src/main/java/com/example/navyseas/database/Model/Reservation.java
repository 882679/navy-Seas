package com.example.navyseas.database.Model;

public class Reservation {

    private int studentID;
    private int activityID;

    public Reservation(int studentID, int activityID) {
        this.studentID = studentID;
        this.activityID = activityID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

}
