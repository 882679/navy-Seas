package com.example.navyseas;

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
    @Test
    public void setUp() {
        activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));

        f = new Family(0, "Carraro");

        // Non va bene perchè l'ID assegnato durante la creazione dell'oggetto Family non
        // corrisponde necessariamente all'ID assegnato dal DB, poichè è un parametro
        // UNIQUE AUTOINCREMENT che parte da 1
        Student s1 = new Student(0, "Alvise", f.getId());
        Student s2 = new Student(0,"Angelo", f.getId());
        Student s3 = new Student(0, "Alessandro", f.getId());
        Student s4 = new Student(0,"Giulia", f.getId());

        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
    }
    @Test
    public void testFamilyName() {
        String cognome = "Carraro";
        try {
            assertEquals(cognome, f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testStudent() {
        /*String name = "Alvise";
        try {
            assertEquals(name, f.getChildren().get(0).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    @Test
    public void testStudentActivity() {
        /*try {
            assertEquals(activityList.get(0).getName(), f.getChildren().get(0).getActivities().get(0).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}