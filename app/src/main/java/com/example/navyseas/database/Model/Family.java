package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.List;

public class Family {
	private ObjectId id;
	private String name;
	private List<Student> children;
	private double price;

	public Family(ObjectId id, String name, List<Student> children, double price) {
		this.id = id;
		this.name = name;
		this.children = children;
		this.price = price;
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

	public List<Student> getChildren() {
		return children;
	}

	public void setChildren(List<Student> children) {
		this.children = children;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Family{" +
				"id=" + id +
				", name='" + name + '\'' +
				", children=" + children +
				", price=" + price +
				'}';
	}
}
