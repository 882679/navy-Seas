package com.example.navyseas.database;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubscribeToActivity {
	public static void main(String[] args) {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Student> studentCollection = database.getCollection("Student", Student.class);
		MongoCollection<Activity> activityCollection = database.getCollection("Activity", Activity.class);
		MongoCollection<Family> familyCollection = database.getCollection("Family", Family.class);

		FindIterable<Student> iteratorStudents = studentCollection.find();
		FindIterable<Activity> iteratorActivities = activityCollection.find();

		ArrayList<Activity> activities = new ArrayList<>();
		for (Activity a : iteratorActivities) {
			activities.add(a);
		}

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

			for (int i = 0; i < activities.size(); i++) {
				System.out.println(i + " : " + activities.get(i).getName() + ", " + activities.get(i).getDay() + ", " + activities.get(i).getPrice());
			}

			System.out.print("Select activity (index): ");
			int indexActivity = input.nextInt();
			input.nextLine();

			Activity activityToUpdate = activities.get(indexActivity);

			if (studentToUpdate.checkActivities(activityToUpdate, students)) {
				List<Activity> list = studentToUpdate.getActivities();
				list.add(activityToUpdate);
				studentToUpdate.setActivities(list);

				studentCollection.updateOne(
					eq("_id", studentToUpdate.getId()),
					set("activities", studentToUpdate.getActivities())
				);

				updateFamily(familyCollection, studentToUpdate);
			} else {
				System.out.println("Error. Try again.");
			}

			System.out.print("Subscribe another student? (Y/N): ");
			choice = input.nextLine();
		} while (choice.equals("Y"));
	}

	public static void updateFamily(MongoCollection<Family> familyCollection, Student s) {
		Family familyToUpdate = familyCollection.find(eq("children._id", s.getId())).first();

		if (familyToUpdate != null) {
			familyToUpdate.updateChildren(s);
			familyCollection.updateOne(
				eq("children._id", s.getId()),
				combine(
					set("children", familyToUpdate.getChildren()),
					set("amount", familyToUpdate.getAmount())
				)
			);
		}
	}
}
