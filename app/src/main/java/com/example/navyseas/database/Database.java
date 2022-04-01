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
