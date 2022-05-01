package com.example.navyseas;

import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testFamily extends TestCase {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper db = new DBHelper(appContext);

    Family navy = db.login("Navy@stud.unive.it", "Navy");

    Family testFamily = new Family(100, "Test", "test@test", "test");



    @Test
    public void test1_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void test2_addFamily() throws InterruptedException {
        db.test_addFamily(testFamily);
        Thread.sleep(100);
        Family f = db.login("test@test", "test");
        assertEquals(testFamily.getName(), f.getName());
    }

    @Test
    public void test3_deleteFamily() {
        db.test_deleteFamily(testFamily);
        assertNull(db.login("test@test", "test"));
    }

    @Test
    public void test4_familyInfo() throws InterruptedException {
        Thread.sleep(100);
        assertEquals("Navy@stud.unive.it", navy.getEmail());
    }

    @Test
    public void test5_familyChildren() {
        ArrayList<Student> children = db.getChildren(navy);
        boolean found = false;
        for (Student student :
                children) {
            if (student.getName().equals("Alvise")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }


}