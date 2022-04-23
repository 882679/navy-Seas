package com.example.navyseas.database.Model;

import androidx.annotation.NonNull;

public class Activity {
	private final int id; // PRIMARY KEY
	private final String name;
	private final String day;
	private final double price;
	private final int capacity;

	public Activity(int id, String name, String day, double price, int capacity) {
		this.id = id;
		this.name = name;
		this.day = day;
		this.price = price;
		this.capacity = capacity;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDay() {
		return day;
	}

	public double getPrice() {
		return price;
	}

	public int getCapacity() {
		return capacity;
	}

	@NonNull
	@Override
	public String toString() {
		return "Activity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", day='" + day + '\'' +
				", price=" + price +
				", capacity=" + capacity +
				'}';
	}
}
