package com.example.navyseas.database.Model;

import androidx.annotation.NonNull;

public class Student {
	private final int id;         // PRIMARY KEY
	private final String name;
	private final int familyID;   // FOREIGN KEY

	public Student(int id, String name, int familyID) {
		this.id = id;
		this.name = name;
		this.familyID = familyID;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getFamilyID() {
		return familyID;
	}

	@NonNull
	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", familyID=" + familyID +
				'}';
	}
}
