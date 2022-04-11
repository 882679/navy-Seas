package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private ObjectId id;
	private String name;
	private List<Activity> activities;

	public Student() {
	}

	public Student(ObjectId id, String name, List<Activity> activities) {
		this.id = id;
		this.name = name;
		this.activities = activities;
	}

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

	public boolean checkActivities(Activity a, ArrayList<Student> students) {
		return !(activities.contains(a)) && checkDay(a) && checkCapacity(a, students);
	}

	private boolean checkCapacity(Activity a, ArrayList<Student> students) {
		int taken = 0;

		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < students.get(i).getActivities().size(); j++) {
				if (students.get(i).getActivities().get(j).getId().equals(a.getId())) taken++;
			}
		}

		return taken < a.getCapacity();
	}

	private boolean checkDay(Activity a) {
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).getDay().equals(a.getDay())) return false;
		}

		return true;
	}
}
