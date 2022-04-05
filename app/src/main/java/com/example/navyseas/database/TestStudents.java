package com.example.navyseas.database;

import com.example.navyseas.database.Model.Student;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;

public class TestStudents {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Student> student = database.getCollection("Student", Student.class);

		Student stu = new Student(new ObjectId(), "Angelo", Collections.emptyList());

		try {
			student.insertOne(stu);
		} catch (MongoException me) {
			System.err.println("Unable to delete due to an error: " + me);
		}

		FindIterable<Student> it = student.find();

		ArrayList<Student> students = new ArrayList<>();
		for (Student s : it) {
			students.add(s);
		}

		for (int i = 0; i < students.size(); i++) {
			System.out.println(students.get(i).toString());
		}
	}

}
