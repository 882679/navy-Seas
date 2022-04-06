package com.example.navyseas.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

public class DatabaseUtility {

	// Metodo che stabilisce una connessione con il client, restituendo il database
	public static MongoDatabase createConnection() throws MongoException {
		DotenvBuilder dotenv = Dotenv.configure().directory("app/src/main/assets").filename("env");

		Dotenv dot = dotenv.load();

		final String USERNAME = dot.get("USERNAME");
		final String PASSWORD = dot.get("PASSWORD");

		// Inserire la stringa fornita all'interno del costruttore
		ConnectionString connectionString = new ConnectionString("mongodb+srv://" + USERNAME + ":" + PASSWORD + "@cluster0.qgnxq.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

		// TODO: Commentare "Codec"
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider
				.builder()
				.automatic(true)
				.build()
		);

		CodecRegistry codecRegistry = fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry
		);

		// Crea un oggetto contenente tutte le impostazioni del client
		MongoClientSettings clientSettings = MongoClientSettings
				.builder()
				.applyConnectionString(connectionString)
				.codecRegistry(codecRegistry).build();

		// Crea la connessione con il client, fornendo le impostazioni desiderate
		MongoClient mongoClient = MongoClients.create(clientSettings);

		return mongoClient.getDatabase("Navy-SEAS");
	}


	public static void deleteAllDocuments(MongoDatabase database) throws MongoException {
		database.listCollectionNames().forEach(collection -> {
			try {
				DeleteResult result = database.getCollection(collection).deleteMany(new Document());
				System.out.println("Deleted document count: " + result.getDeletedCount());
			} catch (MongoException me) {
				System.err.println("Unable to delete due to an error: " + me);
			}
		});
	}

}
