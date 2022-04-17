package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Activity {
	private ObjectId id;
	private String name;
	private String day;
	private double price;
	private int capacity;

	public Activity() {
	}

	public Activity(ObjectId id, String name, String day, double price, int capacity) {
		this.id = id;
		this.name = name;
		this.day = day;
		this.price = price;
		this.capacity = capacity;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Activity activity = (Activity) o;
		return Objects.equals(name, activity.name);
	}

}
