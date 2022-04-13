package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class DataMockup {

    public ArrayList<Reservation> reservations = new ArrayList<>();
    public ArrayList<Activity> activityList = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public Family family;

    public DataMockup() {
        activityList.add(new Activity(new ObjectId(), "Tennis", "Lunedi", 10.0, 20));
        activityList.add(new Activity(new ObjectId(), "Lettura", "Giovedi", 10.0, 30));
        activityList.add(new Activity(new ObjectId(), "Cinema", "Martedi", 10.0, 10));
        activityList.add(new Activity(new ObjectId(), "Bicicletta", "Venerdi", 10.0, 50));
        activityList.add(new Activity(new ObjectId(), "Calcio", "Lunedi", 10.0, 20));
        activityList.add(new Activity(new ObjectId(), "Pattinaggio", "Lunedi", 10.0, 40));
        activityList.add(new Activity(new ObjectId(), "Compiti", "Mercoledi", 10.0, 60));
        activityList.add(new Activity(new ObjectId(), "Nascondino", "Mercoledi", 10.0, 20));
        activityList.add(new Activity(new ObjectId(), "Scacchi", "Giovedi", 10.0, 20));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));
        activityListAlvise.add(activityList.get(1));
        activityListAlvise.add(activityList.get(2));

        ArrayList<Activity> activityListAngelo = new ArrayList<>();
        activityListAngelo.add(activityList.get(2));
        activityListAngelo.add(activityList.get(3));
        activityListAngelo.add(activityList.get(4));

        ArrayList<Activity> activityListAlessandro = new ArrayList<>();
        activityListAlessandro.add(activityList.get(4));
        activityListAlessandro.add(activityList.get(5));
        activityListAlessandro.add(activityList.get(6));

        ArrayList<Activity> activityListGiulia = new ArrayList<>();
        activityListGiulia.add(activityList.get(6));
        activityListGiulia.add(activityList.get(7));
        activityListGiulia.add(activityList.get(8));

        students.add(new Student(new ObjectId(), "Alvise", activityListAlvise));
        students.add(new Student(new ObjectId(),"Angelo", activityListAngelo));
        students.add(new Student(new ObjectId(),"Alessandro", activityListAlessandro));
        students.add(new Student(new ObjectId(),"Giulia", activityListGiulia));

        for (Student s :
                students) {
            for (Activity a :
                    s.getActivities()) {
                reservations.add(new Reservation(a, s));
            }
        }

        family = new Family(new ObjectId(), "Falcarin", students, 50.0);
    }


}
