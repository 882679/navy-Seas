package com.example.navyseas;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class DataMockup {

    public List<Activity> reservations = new ArrayList<>();
    public List<Student> students = new ArrayList<>();
    public Family family;

    public DataMockup() {
        /*students.add(new Student(new ObjectId(), "Alvise", reservations));
        students.add(new Student(new ObjectId(), "Angelo", reservations));
        students.add(new Student(new ObjectId(), "Alessandro", reservations));
        students.add(new Student(new ObjectId(), "Giulia", reservations));*/
        /*reservations.add(new Activity(new ObjectId(), "Tennis", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Lettura", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Cinema", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Bicicletta", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Tennis", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Lettura", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Cinema", "Lunedi", 10.0, students));
        reservations.add(new Activity(new ObjectId(), "Bicicletta", "Lunedi", 10.0, students));*/
        family = new Family(new ObjectId(), "Falcarin", students, 50.0);
    }


}
