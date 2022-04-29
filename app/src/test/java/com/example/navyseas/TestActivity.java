package com.example.navyseas;

import android.content.Context;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.DBHelper;


import junit.framework.TestCase;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


@DisplayName("Activity list")
public class TestActivity extends TestCase {
    public static DBHelper db = new DBHelper(container.getContext());
    public ArrayList<Activity> activityListBefore = new ArrayList<>(getActivities);

    public ArrayList<Activity> activityListNow = new ArrayList<>();

    /*
    *   update an activity
    *   check the update
    *   delete it
    *   check delete
    *   activity before == now
    */
    @DisplayName("Activity Update")
    @Test
    public void ActivityUpdate() {
        Activity activity = new Activity(1, "Scacchi", "Lunedi", 8, 20);
        try {
            assertEquals(activity, activityList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDay() {
        String day = "Martedi";
        try {
            assertEquals(day, activityList.get(1).getDay());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
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