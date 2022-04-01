package com.example.navyseas.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;

import org.bson.Document;

public class CollectionUtility {

	public static void populate(MongoCollection<Document> collection, Document doc) throws MongoException {
		try {
			InsertOneResult result = collection.insertOne(doc);
			System.out.println("Success! Inserted document id: " + result.getInsertedId());
		} catch (MongoException me) {
			System.err.println("Unable to insert due to an error: " + me);
		}
	}

	public static void listAllDocuments(MongoCollection<Document> collection) throws MongoException {
		try {
			collection.find().forEach(System.out::println);
		} catch (MongoException me) {
			System.err.println("Unable to delete due to an error: " + me);
		}
	}

	public static void deleteAllDocuments(MongoCollection<Document> collection) throws MongoException {
		try {
			DeleteResult result = collection.deleteMany(new Document());
			System.out.println("Deleted document count: " + result.getDeletedCount());
		} catch (MongoException me) {
			System.err.println("Unable to delete due to an error: " + me);
		}
	}

}
