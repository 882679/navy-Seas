package com.example.navyseas.database.Model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.MongoCollection;

import org.bson.types.ObjectId;

import java.util.List;

public class Family {
	private int id;
	private String name;

	public Family() {
	}

	public Family(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Family{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	/*public void updateFamily(MongoCollection<Family> familyCollection, Student s) {
		updateChildren(s);
		familyCollection.updateOne(
				eq("children._id", s.getId()),
				combine(
						set("children", children),
						set("amount", amount)
				)
		);
	}

	public void updateChildren(Student s) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getId() == s.getId())
				children.get(i).setActivities(s.getActivities());
		}

		updateAmount();
	}

	public void updateAmount() {
		amount = 0;

		for (int i = 0; i < children.size(); i++) {
			for (int j = 0; j < children.get(i).getActivities().size(); j++) {
				amount += children.get(i).getActivities().get(j).getPrice();
			}
		}
	}*/
}
