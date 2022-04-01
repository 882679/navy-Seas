package com.example.navyseas.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ListDocuments {

	public static void main(String[] args) {
		// Inserire la stringa di connessione al database fornita
		String connectionString = "";

		try (MongoClient client = MongoClients.create(connectionString)) {
			// Connects to specific database
			MongoDatabase db = client.getDatabase("Navy-SEAS");

			MongoCollection<Document> familyCollection = db.getCollection("Family");

			try {
				familyCollection.find().forEach(System.out::println);
			} catch (MongoException me) {
				System.err.println("Unable to delete due to an error: " + me);
			}
		}
	}

}