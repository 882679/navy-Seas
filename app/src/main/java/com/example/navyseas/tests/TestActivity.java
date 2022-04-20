package com.example.navyseas.tests;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends TestCase {
    public ArrayList<Activity> activityList = new ArrayList<>();

    public void setUp() {
        activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));
        activityList.add(new Activity(2, "Lettura", "Martedi", 5, 30));
        activityList.add(new Activity(3, "Disegno", "Mercoledi", 7, 10));
        activityList.add(new Activity(4, "Pallavolo", "Giovedi", 10, 50));
        activityList.add(new Activity(5, "Calcio", "Venerdi", 15, 20));
        activityList.add(new Activity(6, "Aiuto Compiti", "Lunedi", 10, 60));
    }

    public void testActivityList() {
        Activity activity = new Activity(1, "Scacchi", "Lunedi", 8, 20);
        try {
            assertEquals(activity, activityList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDay() {
        String day = "Martedi";
        try {
            assertEquals(day, activityList.get(1).getDay());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCapacityVolleyball() {
        int c = 50;
        try {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i).getName().equals("Pallavolo")) {
                    assertEquals(c, activityList.get(i).getCapacity());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}