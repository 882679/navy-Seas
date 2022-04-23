package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestStudent extends TestCase {
    Student student;
    ArrayList<Activity> activityList = new ArrayList<>();
    public void setUp() {

        /*activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));
        activityList.add(new Activity(2, "Lettura", "Martedi", 5, 30));
        activityList.add(new Activity(3, "Disegno", "Mercoledi", 7, 10));
        activityList.add(new Activity(4, "Pallavolo", "Giovedi", 10, 50));
        activityList.add(new Activity(5, "Calcio", "Venerdi", 15, 20));
        activityList.add(new Activity(6, "Aiuto Compiti", "Lunedi", 10, 60));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));
        activityListAlvise.add(activityList.get(1));
        activityListAlvise.add(activityList.get(2));

        student = new Student(new ObjectId(), "Alvise", activityListAlvise);*/

    }
    @Test
    public void testStudentName() {
        /*String nome = "Alvise";
        try {
            assertEquals(nome, student.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    @Test
    public void testStudentActivities1() {
        /*Activity activity = new Activity(1, "Disegno", "Mercoledi", 7, 10);
        try {
            assertEquals(activity, student.getActivities().get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    @Test
    public void testStudentActivities2() {
        /*Activity activity = new Activity(6, "Aiuto Compiti", "Lunedi", 10, 60);
        try {
            assertFalse(student.getActivities().contains(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}