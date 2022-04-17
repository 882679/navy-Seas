package com.example.navyseas.database.Model;

public class Reservation {

    private Activity activity;
    private Student student;


    public Reservation(Activity activity, Student student) {
        this.activity = activity;
        this.student = student;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
