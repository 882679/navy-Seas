package com.example.navyseas.database;

import com.example.navyseas.database.Model.Family;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;

public class TestFamilies {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Family> family = database.getCollection("Family", Family.class);

		FindIterable<Family> it = family.find();

		ArrayList<Family> families = new ArrayList<>();
		for (Family f : it) {
			families.add(f);
		}

		for (int i = 0; i < families.size(); i++) {
			System.out.println(families.get(i).toString());
		}
	}

}
