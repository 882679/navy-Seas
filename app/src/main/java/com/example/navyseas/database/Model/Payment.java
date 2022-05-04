package com.example.navyseas.database.Model;

public class Payment {
	private final int id;           // PRIMARY KEY
	private final double amount;
	private final String date;
	private final int idFamily;     // FOREIGN KEY

	public Payment(int id, double amount, String date, int idFamily) {
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.idFamily = idFamily;
	}

	public int getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

	public int getIdFamily() {
		return idFamily;
	}

	@Override
	public String toString() {
		return "Payment{" +
				"id=" + id +
				", amount=" + amount +
				", date=" + date +
				", idFamily=" + idFamily +
				'}';
	}
}
