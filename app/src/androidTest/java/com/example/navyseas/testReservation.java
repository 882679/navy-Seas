package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
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
public class testReservation extends TestCase {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper db = new DBHelper(appContext);

    ArrayList<Activity> activityListBefore = new ArrayList<>(db.getActivities());

    Family navy = db.login("Navy@stud.unive.it", "Navy");

    ArrayList<Student> childrenList = db.getChildren(navy);
    ArrayList<Activity> activities = new ArrayList<>(db.getActivities());
    Student student = childrenList.get(childrenList.size()-1);
    Activity activity = activities.get(activities.size()-1);


    Reservation reservation = new Reservation(student.getId(), activity.getId());


    @Test
    public void test1_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void test2_addReservation() throws InterruptedException {
        if(db.checkActivity(student, activity)){
            db.subscribe(reservation);
        }
        Thread.sleep(100);
        assertTrue(db.isStudentAlreadySubscribed(student, activity));
    }

    @Test
    public void test3_deleteReservation() {
        db.unsubscribe(reservation);
        assertFalse(db.isStudentAlreadySubscribed(student, activity));
    }

    @Test
    public void test4_testdb(){
        ArrayList<Activity> activityListNow = new ArrayList<>(db.getActivities());
        assertEquals(activityListBefore.toString(),activityListNow.toString());
    }

}