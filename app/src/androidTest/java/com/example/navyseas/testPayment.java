package com.example.navyseas;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.navyseas.database.DBHelper;
import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Payment;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testPayment extends TestCase {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DBHelper db = new DBHelper(appContext);

    Family navy = db.login("Navy@stud.unive.it", "Navy");

    ArrayList<Payment> paymentListBefore = db.getPayments(navy);

    String dateTest = "3000-01-01";
    double amountTest = 80;
    Payment paymentTest = new Payment(1, amountTest, dateTest, navy.getId());

    @Test
    public void test1_useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.navyseas", appContext.getPackageName());
    }

    @Test
    public void test2_addPayment() throws InterruptedException {
        db.test_pay(paymentTest);
        Thread.sleep(100);
        ArrayList<Payment> payments = db.getPayments(navy);
        assertTrue(payments.contains(paymentTest));
    }

    @Test
    public void test3_paymentAmount() {
        ArrayList<Payment> payments = db.getPayments(navy);
        double amountFound=0;
        for (Payment p:
                payments) {
            if (p.getDate().equals(dateTest)){
                amountFound = p.getAmount();
            }
        }
        assertEquals(amountFound, amountTest);
    }

    @Test
    public void test4_deletePayment() throws InterruptedException {
        db.test_deletePayment(paymentTest);
        Thread.sleep(100);
        ArrayList<Payment> payments = db.getPayments(navy);
        assertFalse(payments.contains(paymentTest));
    }

    @Test
    public void test5_testdb() {
        ArrayList<Payment> paymentsListAfter = db.getPayments(navy);
        assertEquals(paymentListBefore.toString(), paymentsListAfter.toString());
    }

}