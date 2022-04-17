package com.example.navyseas.database;

import com.example.navyseas.database.Model.Family;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.Scanner;

public class TestFamiliesIns {
	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Family> family = database.getCollection("Family", Family.class);

		Scanner input = new Scanner(System.in);

		String choice;
		do {
			System.out.println("Insert family name: ");
			String name = input.nextLine();
			family.insertOne(new Family(new ObjectId(), name, Collections.emptyList(), 0));

			System.out.println("Insert another family? ");
			choice = input.nextLine();
		} while (choice.equals("Y"));
	}
}
