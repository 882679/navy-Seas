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
public class testStudent extends TestCase {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper db = new DBHelper(appContext);

    Family seas = db.login("Seas@stud.unive.it", "Seas");
    Student testStudent = new Student(100, "TestStudent", seas.getId());
    ArrayList<Student> childrenList;


    @Test
    public void test1_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void test2_addStudent() throws InterruptedException {
        db.test_addStudent(testStudent);
        Thread.sleep(100);
        childrenList = db.getChildren(seas);
        boolean found = false;
        for (Student student :
                childrenList) {
            if (student.getName().equals(testStudent.getName())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void test3_deleteStudent() {
        db.test_deleteStudent(testStudent);
        childrenList = db.getChildren(seas);
        boolean found = false;
        for (Student student :
                childrenList) {
            if (student.getName().equals(testStudent.getName())) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

}