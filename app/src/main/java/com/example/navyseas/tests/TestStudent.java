package com.example.navyseas.tests;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TestStudent extends TestCase {
    Student student;
    ArrayList<Activity> activityList = new ArrayList<>();
    public void setUp() {

        activityList.add(new Activity(new ObjectId(), "Scacchi", "Lunedi", 8, 20));
        activityList.add(new Activity(new ObjectId(), "Lettura", "Martedi", 5, 30));
        activityList.add(new Activity(new ObjectId(), "Disegno", "Mercoledi", 7, 10));
        activityList.add(new Activity(new ObjectId(), "Pallavolo", "Giovedi", 10, 50));
        activityList.add(new Activity(new ObjectId(), "Calcio", "Venerdi", 15, 20));
        activityList.add(new Activity(new ObjectId(), "Aiuto Compiti", "Lunedi", 10, 60));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));
        activityListAlvise.add(activityList.get(1));
        activityListAlvise.add(activityList.get(2));

        student = new Student(new ObjectId(), "Alvise", activityListAlvise);

    }

    public void testStudentName() {
        String nome = "Alvise";
        try {
            assertEquals(nome, student.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStudentActivities1() {
        Activity activity = new Activity(new ObjectId(), "Disegno", "Mercoledi", 7, 10);
        try {
            assertEquals(activity, student.getActivities().get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStudentActivities2() {
        Activity activity = new Activity(new ObjectId(), "Aiuto Compiti", "Lunedi", 10, 60);
        try {
            assertFalse(student.getActivities().contains(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}