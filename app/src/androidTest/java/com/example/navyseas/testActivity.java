package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.ArrayList;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testActivity extends TestCase {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper db = new DBHelper(appContext);
    ArrayList<Activity> activities;
    ArrayList<Activity> activityListBefore = db.getActivities();

    Activity testActivity = new Activity(100, "TestName", "TestDescription", "TestDay", 10.0, 10);


    @Test
    public void test1_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void test2_addActivity() throws InterruptedException {
        db.test_addActivity(testActivity);
        Thread.sleep(100);
        activities = db.getActivities();
        boolean found = false;
        for (Activity activity :
                activities) {
            if (activity.getName().equals(testActivity.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void test3_deleteActivity() {
        db.test_deleteActivity(testActivity);
        activities = db.getActivities();
        boolean found = false;
        for (Activity activity :
                activities) {
            if (activity.getName().equals(testActivity.getName())) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    public void test4_testdb() {
        assertEquals(activityListBefore.toString(), db.getActivities().toString());
    }

    @Test
    public void test5_capacityVolleyball() {
        Activity volleyball = db.getActivityByID(4);
        assertEquals(15, volleyball.getCapacity());
    }


}