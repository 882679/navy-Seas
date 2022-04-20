package com.example.navyseas.tests;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;

import junit.framework.TestCase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TestReservation extends TestCase {

    public ArrayList<Reservation> reservations = new ArrayList<>();
    public ArrayList<Activity> activityList = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    ArrayList<Activity> activityListAlvise = new ArrayList<>();
    ArrayList<Activity> activityListAngelo = new ArrayList<>();

    public void setUp() {

        activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));
        activityList.add(new Activity(2, "Lettura", "Martedi", 5, 30));
        activityList.add(new Activity(3, "Disegno", "Mercoledi", 7, 10));
        activityList.add(new Activity(4, "Pallavolo", "Giovedi", 10, 50));
        activityList.add(new Activity(5, "Calcio", "Venerdi", 15, 20));
        activityList.add(new Activity(6, "Aiuto Compiti", "Lunedi", 10, 60));

        activityListAlvise.add(activityList.get(0));
        activityListAlvise.add(activityList.get(1));
        activityListAlvise.add(activityList.get(2));
        activityListAlvise.add(activityList.get(3));

        activityListAngelo.add(activityList.get(2));
        activityListAngelo.add(activityList.get(3));
        activityListAngelo.add(activityList.get(4));
        activityListAngelo.add(activityList.get(5));

        students.add(new Student(new ObjectId(), "Alvise", activityListAlvise));
        students.add(new Student(new ObjectId(), "Angelo", activityListAngelo));

        for (Student s :
                students) {
            for (Activity a :
                    s.getActivities()) {
                reservations.add(new Reservation(a, s));
            }
        }
    }

    public void testBookingStudent() {
        Student student = new Student(new ObjectId(), "Alvise", activityListAlvise);
        boolean found = false;
        try {
            for (Reservation reservation :
                    reservations) {
                if (reservation.getStudent().getName().equals(student.getName())) {
                    found = true;
                }
            }
            assertTrue(found);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBookingActivity() {
        Activity activity = new Activity(5, "Calcio", "Venerdi", 15, 20);
        boolean found = false;
        try {
            for (Reservation reservation :
                    reservations) {
                if (reservation.getActivity().getName().equals(activity.getName())) {
                    found = true;
                }
            }
            assertTrue(found);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}