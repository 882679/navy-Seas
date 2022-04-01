package com.example.navyseas.database;

import static com.mongodb.client.model.Filters.eq;

import androidx.collection.ArraySet;

import com.example.navyseas.database.Model.Activity;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Activity> activities = database.getCollection("Activity", Activity.class);

		// TEST: Stampa le caratteristiche di determinata attività

		// Activity activity = activities.find(eq("name", "Chess")).first();

		// System.out.println(activity.getDay());
		// System.out.println(activity.getName());
		// System.out.println(activity.getPrice());
		// System.out.println(activity.getStudents());

		// TEST: Stampa tutte le attività presenti nella collection Activity dopo averle correttamente salvate
		FindIterable<Activity> it = activities.find();

		ArrayList<Activity> activity = new ArrayList<>();
		for (Activity a :it) {
			activity.add(a);
		}

		for (int i = 0; i < activity.size(); i++) {
			System.out.println(activity.get(i).toString());
		}
	}

}
