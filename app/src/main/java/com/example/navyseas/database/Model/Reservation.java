package com.example.navyseas.database.Model;

import androidx.annotation.NonNull;

public class Reservation {
	private final int studentID;  // FOREIGN KEY
	private final int activityID; // FOREIGN KEY

	public Reservation(int studentID, int activityID) {
		this.studentID = studentID;
		this.activityID = activityID;
	}

	public int getStudentID() {
		return studentID;
	}

	public int getActivityID() {
		return activityID;
	}

	@NonNull
	@Override
	public String toString() {
		return "Reservation{" +
				"studentID=" + studentID +
				", activityID=" + activityID +
				'}';
	}
}
