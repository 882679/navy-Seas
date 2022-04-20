package com.example.navyseas.tests;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;

import org.bson.types.ObjectId;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestFamily extends TestCase {
    Family f;
    ArrayList<Activity> activityList = new ArrayList<>();
    public void setUp() {

        activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));

        Student s1 = new Student(new ObjectId(), "Alvise", activityListAlvise);
        Student s2 = new Student(new ObjectId(),"Angelo", new ArrayList<>());
        Student s3 = new Student(new ObjectId(), "Alessandro", new ArrayList<>());
        Student s4 = new Student(new ObjectId(),"Giulia", new ArrayList<>());

        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);

        f = new Family(new ObjectId(), "Carraro", studentList, 50.0);
    }

    public void testFamilyName() {
        String cognome = "Carraro";
        try {
            assertEquals(cognome, f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStudent() {
        String name = "Alvise";
        try {
            assertEquals(name, f.getChildren().get(0).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStudentActivity() {
        try {
            assertEquals(activityList.get(0).getName(), f.getChildren().get(0).getActivities().get(0).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}