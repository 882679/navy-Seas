package com.example.navyseas.database.Model;

import androidx.annotation.NonNull;

public class Activity {
	private int id; // PRIMARY KEY
	private String name;
	private String day;
	private double price;
	private int capacity;

	public Activity(int id, String name, String day, double price, int capacity) {
		this.id = id;
		this.name = name;
		this.day = day;
		this.price = price;
		this.capacity = capacity;
	}

	public Activity() {
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
