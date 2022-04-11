package com.example.navyseas.database;

import com.mongodb.client.MongoDatabase;

public class DeleteAll {
	public static void main(String[] args) {
		MongoDatabase db = DatabaseUtility.createConnection();
		DatabaseUtility.deleteAllDocuments(db);
	}
}
