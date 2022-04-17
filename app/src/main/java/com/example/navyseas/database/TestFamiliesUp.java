package com.example.navyseas.database;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.example.navyseas.database.Model.Family;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestFamiliesUp {
	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Family> family = database.getCollection("Family", Family.class);

		family.updateMany(
			eq("amount", 0),
			set("amount", 0.0)
		);
	}
}
