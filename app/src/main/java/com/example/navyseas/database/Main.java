package com.example.navyseas.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

	public static void main(String[] args) throws MongoException {
		MongoDatabase db = Database.createConnection();
		MongoCollection<Document> familyCollection = db.getCollection("Family");
		familyCollection.find().forEach(System.out::println);
	}

}
