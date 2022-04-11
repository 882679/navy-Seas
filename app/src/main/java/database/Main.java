package database;

import static com.mongodb.client.model.Filters.eq;

import android.annotation.SuppressLint;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

    public static void main( String[] args ) {
        @SuppressLint("AuthLeak")
        String connectionString = "mongodb+srv://root:Y7ri9Ehl5SPlpuVs@cluster0.qgnxq.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

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
