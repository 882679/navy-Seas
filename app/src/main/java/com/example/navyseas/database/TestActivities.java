package com.example.navyseas.database;

import com.example.navyseas.database.Model.Activity;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivities {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Activity> activities = database.getCollection("Activity", Activity.class);

		Activity act = new Activity(new ObjectId(), "Test", "Monday", 9, Collections.emptyList());

		try {
			activities.insertOne(act);
		} catch (MongoException me) {
			System.err.println("Unable to delete due to an error: " + me);
		}

		FindIterable<Activity> it = activities.find();

		ArrayList<Activity> activity = new ArrayList<>();
		for (Activity a : it) {
			activity.add(a);
		}

		for (int i = 0; i < activity.size(); i++) {
			System.out.println(activity.get(i).toString());
		}
	}

}
