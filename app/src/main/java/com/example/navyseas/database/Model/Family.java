package com.example.navyseas.database.Model;

import androidx.annotation.NonNull;

public class Family {
	private final int id; // PRIMARY KEY
	private final String name;

	public Family(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@NonNull
	@Override
	public String toString() {
		return "Family{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
