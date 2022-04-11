package com.example.navyseas.database;

import com.example.navyseas.database.Model.Family;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class TestFamilies {
	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Family> familyCollection = database.getCollection("Family", Family.class);

		FindIterable<Family> it = familyCollection.find();

		ArrayList<Family> families = new ArrayList<>();
		for (Family f : it) {
			families.add(f);
		}

		for (int i = 0; i < families.size(); i++) {
			System.out.println(families.get(i).toString());
		}
	}
}
