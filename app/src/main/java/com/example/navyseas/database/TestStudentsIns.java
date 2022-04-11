package com.example.navyseas.database;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Student;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TestStudentsIns {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Student> studentCollection = database.getCollection("Student", Student.class);
		MongoCollection<Family> familyCollection = database.getCollection("Family", Family.class);

		Scanner input = new Scanner(System.in);
		String choice;

		FindIterable<Family> it = familyCollection.find();

		ArrayList<Family> families = new ArrayList<>();
		for (Family f : it) {
			families.add(f);
		}

		do {
			for (int i = 0; i < families.size(); i++) {
				System.out.println(i + " - " + families.get(i).getName());
			}

			System.out.print("Insert name: ");
			String name = input.nextLine();

			System.out.print("Select family (index): ");
			int index = input.nextInt();
			input.nextLine();

			try {
				Student s = new Student(new ObjectId(), name, Collections.emptyList());
				studentCollection.insertOne(s);
				List<Student> list = families.get(index).getChildren();
				list.add(s);
				families.get(index).setChildren(list);

				familyCollection.updateOne(
					eq("_id", families.get(index).getId()),
					set("children", families.get(index).getChildren())
				);
			} catch (MongoException me) {
				System.err.println("Unable to delete due to an error: " + me);
			}

			System.out.print("Insert another student? (Y/N): ");
			choice = input.nextLine();
		} while (choice.equals("Y"));
	}

}
