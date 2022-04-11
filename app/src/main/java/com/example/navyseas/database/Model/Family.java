package com.example.navyseas.database.Model;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class Family {
	private ObjectId id;
	private String name;
	private List<Student> children;
	private double amount;

	public Family() {
	}

	public Family(ObjectId id, String name, List<Student> children, double amount) {
		this.id = id;
		this.name = name;
		this.children = children;
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

	public List<Student> getChildren() {
		return children;
	}

	public void setChildren(List<Student> children) {
		this.children = children;
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
				", children=" + children +
				", amount=" + amount +
				'}';
	}

	public void updateAmount() {
		amount = 0;

		for (int i = 0; i < children.size(); i++) {
			for (int j = 0; j < children.get(i).getActivities().size(); j++) {
				amount += children.get(i).getActivities().get(j).getPrice();
			}
		}
	}

	public void updateChildren(Student s) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getId().equals(s.getId()))
				children.get(i).setActivities(s.getActivities());
		}

		updateAmount();
	}

}
