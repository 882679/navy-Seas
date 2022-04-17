package com.example.navyseas.database;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Scanner;

public class UnsubscribeTest {
	public static void main(String[] args) {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Student> studentCollection = database.getCollection("Student", Student.class);
		MongoCollection<Family> familyCollection = database.getCollection("Family", Family.class);

		FindIterable<Student> iteratorStudents = studentCollection.find();

		ArrayList<Student> students = new ArrayList<>();
		for (Student s : iteratorStudents) {
			students.add(s);
		}

		Scanner input = new Scanner(System.in);
		String choice;

		do {
			for (int i = 0; i < students.size(); i++) {
				System.out.println(i + " : " + students.get(i).toString());
			}

			System.out.print("Select student (index): ");
			int indexStudent = input.nextInt();
			input.nextLine();

			Student studentToUpdate = students.get(indexStudent);

			for (int i = 0; i < studentToUpdate.getActivities().size(); i++) {
				System.out.println(i + " : " + studentToUpdate.getActivities().get(i).toString());
			}

			System.out.print("Select activity (index): ");
			int indexActivity = input.nextInt();
			input.nextLine();

			Activity activityToUpdate = studentToUpdate.getActivities().get(indexActivity);

			studentToUpdate.unsubscribe(familyCollection, studentCollection, activityToUpdate);

			System.out.print("Unsubscribe another student? (Y/N): ");
			choice = input.nextLine();
		} while (choice.equals("Y"));
	}
}
