package com.example.navyseas.database;

import com.example.navyseas.database.Model.Activity;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class TestActivities {

	public static void main(String[] args) throws MongoException {
		MongoDatabase database = DatabaseUtility.createConnection();

		MongoCollection<Activity> activities = database.getCollection("Activity", Activity.class);

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
