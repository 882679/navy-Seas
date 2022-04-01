package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

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
}
