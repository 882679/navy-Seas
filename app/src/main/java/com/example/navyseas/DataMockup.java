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
        activityList.add(new Activity(1, "Scacchi", "Lunedi", 8, 20));
        activityList.add(new Activity(2, "Lettura", "Martedi", 5, 30));
        activityList.add(new Activity(3, "Disegno", "Mercoledi", 7, 10));
        activityList.add(new Activity(4, "Pallavolo", "Giovedi", 10, 50));
        activityList.add(new Activity(5, "Calcio", "Venerdi", 15, 20));
        activityList.add(new Activity(6, "Aiuto Compiti", "Lunedi", 10, 60));

        ArrayList<Activity> activityListAlvise = new ArrayList<>();
        activityListAlvise.add(activityList.get(0));
        activityListAlvise.add(activityList.get(1));
        activityListAlvise.add(activityList.get(2));

        ArrayList<Activity> activityListAngelo = new ArrayList<>();
        activityListAngelo.add(activityList.get(5));
        activityListAngelo.add(activityList.get(4));
        activityListAngelo.add(activityList.get(3));

        ArrayList<Activity> activityListAlessandro = new ArrayList<>();
        activityListAlessandro.add(activityList.get(0));
        activityListAlessandro.add(activityList.get(4));
        activityListAlessandro.add(activityList.get(5));

        ArrayList<Activity> activityListGiulia = new ArrayList<>();
        activityListGiulia.add(activityList.get(3));
        activityListGiulia.add(activityList.get(1));
        activityListGiulia.add(activityList.get(4));

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
