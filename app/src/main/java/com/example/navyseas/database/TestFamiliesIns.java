package com.example.navyseas.database;

import com.example.navyseas.database.Model.Family;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.Collections;

public class TestFamiliesIns {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Family> family = database.getCollection("Family", Family.class);

		family.insertOne(new Family(new ObjectId(), "Alvise", Collections.emptyList(), 6.5));
	}

}
