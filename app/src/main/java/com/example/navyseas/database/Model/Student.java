package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.List;

public class Student {
	private ObjectId id;
	private Family family;
	private String name;
	private List<Activity> activities;

	public Student(ObjectId id, String name, List<Activity> activities) {
		this.id = id;
		this.name = name;
		this.activities = activities;
	}

	public Student(ObjectId id, Family family, String name, List<Activity> activities) {
		this.id = id;
		this.family = family;
		this.name = name;
		this.activities = activities;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
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
				", family=" + family +
				", name='" + name + '\'' +
				", activities=" + activities +
				'}';
	}
}
