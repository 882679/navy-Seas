package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.List;

public class Family {
	private ObjectId id;
	private String name;
	private List<Student> students;
	private double amount;

	public Family() {
	}

	public Family(ObjectId id, String name, List<Student> students, double amount) {
		this.id = id;
		this.name = name;
		this.students = students;
		this.amount = amount;
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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Family{" +
				"id=" + id +
				", name='" + name + '\'' +
				", students=" + students +
				", amount=" + amount +
				'}';
	}
}
