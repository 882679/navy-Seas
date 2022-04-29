package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;
import com.example.navyseas.database.DBHelper;

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
    ArrayList<Activity> activityListBefore = new ArrayList<>(db.getActivities());

    Student pippo = db.getChildren(db.getFamilies().get(1)).get(1);
    Activity activity = db.getActivityByID(1);
    Reservation res = new Reservation(pippo.getId(),activity.getId());

    /*
     *   update an activity
     *   check the update
     *   delete it
     *   check delete
     *   activity before == now
     */

    @Test
    public void A_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void B_NewActivity(){
        db.subscribe(res);
        assertEquals(true, (db.isStudentAlreadySubscribed(pippo, activity)));
    }

    @Test
    public void C_DeleteActivity(){
        db.unsubscribe(res);
        assertEquals(false,(db.isStudentAlreadySubscribed(pippo, activity)));
    }

    @Test
    public void Z_testdb(){
        ArrayList<Activity> activityListNow = new ArrayList<>(db.getActivities());
        assertEquals(activityListBefore.toString(),activityListNow.toString());
    }


}