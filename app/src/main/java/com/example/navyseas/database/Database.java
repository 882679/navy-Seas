package com.example.navyseas.database;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;

import org.bson.Document;

public class Database {
    private static final String connectionString = "";

    public static MongoDatabase createConnection() throws MongoException {
        return MongoClients.create(connectionString).getDatabase("Navy-SEAS");
    }

    public static void populateCollection(MongoCollection<Document> collection, Document doc) {
        try {
            InsertOneResult result = collection.insertOne(doc);
            System.out.println("Success! Inserted document id: " + result.getInsertedId());
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }

    public static void listAllDocumentsFromCollection(MongoCollection<Document> collection) {
        try {
            collection.find().forEach(System.out::println);
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public static void deleteAllDocumentsFromCollection(MongoCollection<Document> collection) {
        try {
            DeleteResult result = collection.deleteMany(new Document());
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public static void deleteAllDocuments(MongoDatabase db) {
        // Deletes all documents in the database
        db.listCollectionNames().forEach(collection -> {
            try {
                DeleteResult result = db.getCollection(collection).deleteMany(new Document());
                System.out.println("Deleted document count: " + result.getDeletedCount());
            } catch (MongoException me) {
                System.err.println("Unable to delete due to an error: " + me);
            }
        });
    }

}
