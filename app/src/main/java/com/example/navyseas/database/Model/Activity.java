package com.example.navyseas.database.Model;

public class Activity {
	private int id; // PRIMARY KEY
	private String name;
	private String description;
	private String day;
	private double price;
	private int capacity;
	private String description;

	public Activity(int id, String name, String description, String day, double price, int capacity) {
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
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

	@Override
	public String toString() {
		return "Activity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", day='" + day + '\'' +
				", price=" + price +
				", capacity=" + capacity +
				'}';
	}
}
