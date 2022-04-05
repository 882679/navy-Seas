package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.List;

public class Activity {
	private ObjectId id;
	private String name;
	private String day;
	private double price;
	private List<Student> students;

	public Activity() {
	}

	public Activity(ObjectId id, String name, String day, double price, List<Student> students) {
		this.id = id;
		this.name = name;
		this.day = day;
		this.price = price;
		this.students = students;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Activity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", day='" + day + '\'' +
				", price=" + price +
				", students=" + students +
				'}';
	}
}
