package com.example.navyseas.database.Model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.example.navyseas.MainActivity;
import com.mongodb.client.MongoCollection;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private ObjectId id;
	private String name;
	private List<Activity> activities;

	// Constructors
	public Student() {
	}

	public Student(ObjectId id, String name, List<Activity> activities) {
		this.id = id;
		this.name = name;
		this.activities = activities;
	}

	// Getters & Setters
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", activities=" + activities +
				'}';
	}

	public boolean addActivity(Activity activity) {
		if (checkActivities(activity, MainActivity.dataMockup.students)) {
			this.activities.add(activity);
			return true;
		}
		return false;
	}

	public void removeActivity(int index) {
		this.activities.remove(index);
	}
	// Checks if specific student can subscribe to a specific activity
	public boolean checkActivities(Activity a, ArrayList<Student> students) {
		return !(activities.contains(a)) && checkDay(a) && checkCapacity(a, students);
	}

	// Checks if there are "places" left for a specific activity
	private boolean checkCapacity(Activity a, ArrayList<Student> students) {
		int taken = 0;

		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < students.get(i).getActivities().size(); j++) {
				if (students.get(i).getActivities().get(j).getId().equals(a.getId())) taken++;
			}
		}

		return taken < a.getCapacity();
	}

	// Checks if the specified student is already subscribed for another
	// activity that occurs on the same day
	private boolean checkDay(Activity a) {
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).getDay().equals(a.getDay())) return false;
		}

		return true;
	}

	public void subscribe(MongoCollection<Family> familyCollection, MongoCollection<Student> studentCollection, ArrayList<Student> students, Activity a) {
		if (this.checkActivities(a, students)) {
			activities.add(a);

			studentCollection.updateOne(
					eq("_id", id),
					set("activities", activities)
			);

			Family familyToUpdate = familyCollection.find(eq("children._id", id)).first();
			if (familyToUpdate != null) familyToUpdate.updateFamily(familyCollection, this);
		} else System.out.println("Error. Try again.");
	}

	public void unsubscribe(MongoCollection<Family> familyCollection, MongoCollection<Student> studentCollection, Activity a) {
		activities.remove(a);

		studentCollection.updateOne(
				eq("_id", id),
				set("activities", activities)
		);

		Family familyToUpdate = familyCollection.find(eq("children._id", id)).first();
		if (familyToUpdate != null) familyToUpdate.updateFamily(familyCollection, this);
	}
}
