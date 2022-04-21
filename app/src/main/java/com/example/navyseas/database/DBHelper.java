package com.example.navyseas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.navyseas.database.Model.Activity;
import com.example.navyseas.database.Model.Family;
import com.example.navyseas.database.Model.Reservation;
import com.example.navyseas.database.Model.Student;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	public static final String ACTIVITY_TABLE = "ACTIVITY";
	public static final String ACTIVITY_COLUMN_NAME = "NAME";
	public static final String ACTIVITY_COLUMN_DAY = "DAY";
	public static final String ACTIVITY_COLUMN_PRICE = "PRICE";
	public static final String ACTIVITY_COLUMN_CAPACITY = "CAPACITY";
	public static final String ACTIVITY_COLUMN_ID = "ID";

	public static final String FAMILY_TABLE = "FAMILY";
	public static final String FAMILY_COLUMN_NAME = "NAME";
	public static final String FAMILY_COLUMN_ID = "ID";

	public static final String STUDENT_TABLE = "STUDENT";
	public static final String STUDENT_COLUMN_NAME = "NAME";
	public static final String STUDENT_COLUMN_FAMILY_ID = "FAMILY_ID";
	public static final String STUDENT_COLUMN_ID = "ID";

	public static final String RESERVATION_TABLE = "RESERVATION";
	public static final String RESERVATION_COLUMN_STUDENT_ID = "STUDENT_ID";
	public static final String RESERVATION_COLUMN_ACTIVITY_ID = "ACTIVITY_ID";

	public DBHelper(@Nullable Context context) {
		super(context, "Navy-SEAS.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableQuery, populateTableQuery;

		createTableQuery = "CREATE TABLE " + ACTIVITY_TABLE + " (" +
				ACTIVITY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ACTIVITY_COLUMN_NAME + " TEXT, " +
				ACTIVITY_COLUMN_DAY + " TEXT, " +
				ACTIVITY_COLUMN_PRICE + " REAL, " +
				ACTIVITY_COLUMN_CAPACITY + " INTEGER)";
		db.execSQL(createTableQuery);

		createTableQuery = "CREATE TABLE " + FAMILY_TABLE + " (" +
				FAMILY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				FAMILY_COLUMN_NAME + " TEXT)";
		db.execSQL(createTableQuery);

		createTableQuery = "CREATE TABLE " + STUDENT_TABLE + " (" +
				STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				STUDENT_COLUMN_NAME + " TEXT, " +
				STUDENT_COLUMN_FAMILY_ID + " INTEGER, " +
				"CONSTRAINT FAMILY_FK FOREIGN KEY (" + STUDENT_COLUMN_FAMILY_ID + ")" +
				"REFERENCES " + FAMILY_TABLE + " (" + FAMILY_COLUMN_ID + "))";
		db.execSQL(createTableQuery);

		createTableQuery = "CREATE TABLE " + RESERVATION_TABLE + " (" +
				RESERVATION_COLUMN_STUDENT_ID + " INTEGER, " +
				RESERVATION_COLUMN_ACTIVITY_ID + " INTEGER, " +
				"CONSTRAINT RESERVATION_PK PRIMARY KEY (" +
				RESERVATION_COLUMN_STUDENT_ID + ", " + RESERVATION_COLUMN_ACTIVITY_ID + "), " +
				"CONSTRAINT STUDENT_FK FOREIGN KEY (" + RESERVATION_COLUMN_STUDENT_ID + ") " +
				"REFERENCES " + STUDENT_TABLE + " (" + STUDENT_COLUMN_ID + "), " +
				"CONSTRAINT ACTIVITY_FK FOREIGN KEY (" + RESERVATION_COLUMN_ACTIVITY_ID + ") " +
				"REFERENCES " + ACTIVITY_TABLE + " (" + ACTIVITY_COLUMN_ID + "));";
		db.execSQL(createTableQuery);

		populateTableQuery = "INSERT INTO " + ACTIVITY_TABLE +
				" (" + ACTIVITY_COLUMN_NAME + ", " + ACTIVITY_COLUMN_DAY + ", " +
				ACTIVITY_COLUMN_PRICE + ", " + ACTIVITY_COLUMN_CAPACITY + ")" +
				" VALUES " + "('Scacchi', 'Lunedi', 8, 20), " +
				"('Lettura', 'Martedi', 7, 30), " +
				"('Disegno', 'Mercoledi', 5, 10), " +
				"('Pallavolo', 'Giovedi', 10, 50), " +
				"('Calcio', 'Venerdi', 15, 20), " +
				"('Aiuto compiti', 'Lunedi', 10, 60)";
		db.execSQL(populateTableQuery);

		populateTableQuery = "INSERT INTO " + FAMILY_TABLE + " (" + FAMILY_COLUMN_NAME + ")" +
				" VALUES ('Navy'), ('Seas')";
		db.execSQL(populateTableQuery);

		populateTableQuery = "INSERT INTO " + STUDENT_TABLE +
				" (" + STUDENT_COLUMN_NAME + ", " + STUDENT_COLUMN_FAMILY_ID + ")" +
				" VALUES " +
				"('Angelo', 1), " +
				"('Giulia', 1), " +
				"('Alvise', 1), " +
				"('Alessandro', 1), " +
				"('Ivan', 2)";
		db.execSQL(populateTableQuery);

		populateTableQuery = "INSERT INTO " + RESERVATION_TABLE +
				" VALUES (1, 1), (1, 2), (2, 2), (2, 1), (5, 1)";
		db.execSQL(populateTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	public Family getFamily(Student s) {
		String query = "SELECT * FROM " + FAMILY_TABLE +
				" WHERE " + FAMILY_COLUMN_ID + " = " + s.getFamilyID();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (!cursor.moveToFirst()) return null;

		else {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			int amount = cursor.getInt(2);

			cursor.close();
			db.close();

			return new Family(id, name);
		}
	}

	public ArrayList<Student> getChildren(Family f) {
		ArrayList<Student> children = new ArrayList<>();

		String query = "SELECT * FROM " + STUDENT_TABLE +
				" WHERE " + STUDENT_COLUMN_FAMILY_ID + " = " + f.getId();

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int familyID = cursor.getInt(2);

				Student s = new Student(id, name, familyID);
				children.add(s);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return children;
	}

	public boolean subscribe(Reservation r) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(RESERVATION_COLUMN_STUDENT_ID, r.getStudentID());
		cv.put(RESERVATION_COLUMN_ACTIVITY_ID, r.getActivityID());

		return db.insert(RESERVATION_TABLE, null, cv) != -1;
	}

	public boolean subscribe(int student_id, int activity_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(RESERVATION_COLUMN_STUDENT_ID, student_id);
		cv.put(RESERVATION_COLUMN_ACTIVITY_ID, activity_id);

		return db.insert(RESERVATION_TABLE, null, cv) != -1;
	}

	public boolean unsubscribe(Reservation r) {
		SQLiteDatabase db = this.getWritableDatabase();

		String query = "DELETE FROM " + RESERVATION_TABLE +
				" WHERE " + RESERVATION_COLUMN_STUDENT_ID + " = " + r.getStudentID() +
				" AND " + RESERVATION_COLUMN_ACTIVITY_ID + " = " + r.getActivityID();

		Cursor cursor = db.rawQuery(query, null);

		return cursor.moveToFirst();
	}

	public boolean unsubscribe(int student_id, int activity_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		String query = "DELETE FROM " + RESERVATION_TABLE +
				" WHERE " + RESERVATION_COLUMN_STUDENT_ID + " = " + student_id +
				" AND " + RESERVATION_COLUMN_ACTIVITY_ID + " = " + activity_id;

		Cursor cursor = db.rawQuery(query, null);

		return cursor.moveToFirst();
	}

	public ArrayList<Activity> getActivities() {
		ArrayList<Activity> activities = new ArrayList<>();

		String query = "SELECT * FROM " + ACTIVITY_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String day = cursor.getString(2);
				double price = cursor.getDouble(3);
				int capacity = cursor.getInt(4);

				Activity a = new Activity(id, name, day, price, capacity);
				activities.add(a);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return activities;
	}


	public ArrayList<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<>();

		String query = "SELECT * FROM " + STUDENT_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				int familyID = cursor.getInt(2);

				Student s = new Student(id, name, familyID);
				students.add(s);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return students;
	}

	public int getFamilyAmount(Family f) {
		int amount = 0;

		String query = "" +
				"SELECT SUM(" + ACTIVITY_COLUMN_PRICE + ")" +
				"FROM " + ACTIVITY_TABLE + " a " +
				"INNER JOIN " + RESERVATION_TABLE + " r ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID + " " +
				"INNER JOIN " + STUDENT_TABLE + " s ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID + " " +
				"INNER JOIN " + FAMILY_TABLE + " f ON s." + STUDENT_COLUMN_FAMILY_ID + " = f." + FAMILY_COLUMN_ID + " " +
				"AND s." + STUDENT_COLUMN_FAMILY_ID + " = " + f.getId() + " " +
				"GROUP BY s." + STUDENT_COLUMN_ID + " ";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				amount += cursor.getInt(0);
			} while (cursor.moveToNext());
		}

		return amount;
	}

	public int getStudentAmount(Student s) {
		int amount = 0;

		String query = "" +
				"SELECT SUM(" + ACTIVITY_COLUMN_PRICE + ")" +
				"FROM " + ACTIVITY_TABLE + " a" +
				" INNER JOIN " + RESERVATION_TABLE + " r ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID +
				" INNER JOIN " + STUDENT_TABLE + " s ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID +
				" INNER JOIN " + FAMILY_TABLE + " f ON s." + STUDENT_COLUMN_FAMILY_ID + " = f." + FAMILY_COLUMN_ID +
				" AND s." + STUDENT_COLUMN_ID + " = " + s.getId() +
				" GROUP BY s." + STUDENT_COLUMN_ID + " ";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				amount += cursor.getInt(0);
			} while (cursor.moveToNext());
		}

		return amount;
	}

	public ArrayList<Reservation> getChildrenReservations(Family f) {
		ArrayList<Reservation> reservations = new ArrayList<>();

		String query = "SELECT * FROM " + RESERVATION_TABLE + " r " +
				" INNER JOIN " + STUDENT_TABLE + " s ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID +
				" INNER JOIN " + FAMILY_TABLE + " f ON f." + FAMILY_COLUMN_ID + " = s." + STUDENT_COLUMN_FAMILY_ID +
				" AND f." + FAMILY_COLUMN_ID + " = " + f.getId();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int student_id = cursor.getInt(0);
				int activity_id = cursor.getInt(1);

				reservations.add(new Reservation(student_id, activity_id));
			} while (cursor.moveToNext());
		}

		return reservations;
	}

	public ArrayList<Family> getFamilies() {
		ArrayList<Family> families = new ArrayList<>();

		String query = "SELECT * FROM " + FAMILY_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);

				families.add(new Family(id, name));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return families;
	}

	public ArrayList<Reservation> getReservations() {
		ArrayList<Reservation> reservations = new ArrayList<>();

		String query = "SELECT * FROM " + RESERVATION_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int student_id = cursor.getInt(0);
				int activity_id = cursor.getInt(1);

				reservations.add(new Reservation(student_id, activity_id));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return reservations;
	}

	public ArrayList<Reservation> getReservations(Activity a) {
		ArrayList<Reservation> reservations = new ArrayList<>();

		String query = "SELECT * FROM " + RESERVATION_TABLE + " r " +
				"INNER JOIN " + ACTIVITY_TABLE + " a " +
				"ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID +
				" AND a." + ACTIVITY_COLUMN_ID + " = " + a.getId();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int student_id = cursor.getInt(0);
				int activity_id = cursor.getInt(1);

				reservations.add(new Reservation(student_id, activity_id));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return reservations;
	}

	public ArrayList<Reservation> getReservations(Family f) {
		ArrayList<Reservation> reservations = new ArrayList<>();

		String query = "SELECT * FROM " + RESERVATION_TABLE + " r " +
				"INNER JOIN " + ACTIVITY_TABLE + " a " +
				"ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID +
				" AND a." + FAMILY_COLUMN_ID + " = " + f.getId();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int student_id = cursor.getInt(0);
				int activity_id = cursor.getInt(1);

				reservations.add(new Reservation(student_id, activity_id));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return reservations;
	}

	public ArrayList<Reservation> getStudentReservations(Student s) {
		ArrayList<Reservation> reservations = new ArrayList<>();

		String query = "SELECT * FROM " + RESERVATION_TABLE +
				" WHERE " + RESERVATION_COLUMN_STUDENT_ID + " = " + s.getId();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int student_id = cursor.getInt(0);
				int activity_id = cursor.getInt(1);

				reservations.add(new Reservation(student_id, activity_id));
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return reservations;
	}

	public ArrayList<Activity> getStudentActivities(Student s) {
		ArrayList<Reservation> reservations = this.getStudentReservations(s);
		ArrayList<Activity> activities = this.getActivities();
		ArrayList<Activity> studentActivities = new ArrayList<>();

		for (int i = 0; i < reservations.size(); i++) {
			for (int j = 0; j < activities.size(); j++) {
				if (reservations.get(i).getActivityID() == activities.get(j).getId())
					studentActivities.add(activities.get(j));
			}
		}

		return studentActivities;
	}

	public boolean isStudentBusy(Student s, Activity a) {
		ArrayList<Activity> studentActivities = this.getStudentActivities(s);

		for (int i = 0; i < studentActivities.size(); i++) {
			if (studentActivities.get(i).getDay().equals(a.getDay())) return true;
		}

		return false;
	}

	public boolean isStudentAlreadySubscribed(Student s, Activity a) {
		ArrayList<Activity> studentActivities = this.getStudentActivities(s);

		for (int i = 0; i < studentActivities.size(); i++) {
			if (studentActivities.get(i).getId() == a.getId())
				return true;
		}

		return false;
	}

	public boolean checkCapacity(Student s, Activity a) {
		ArrayList<Reservation> reservations = this.getReservations(a);

		return reservations.size() < a.getCapacity();
	}

	// Checks if specific student can subscribe to a specific activity
	public boolean checkActivity(Student s, Activity a) {
		return !(isStudentAlreadySubscribed(s, a)) && !isStudentBusy(s, a) && checkCapacity(s, a);
	}

	/*public boolean addActivity(Activity a) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(ACTIVITY_COLUMN_NAME, a.getName());
		cv.put(ACTIVITY_COLUMN_DAY, a.getDay());
		cv.put(ACTIVITY_COLUMN_PRICE, a.getPrice());
		cv.put(ACTIVITY_COLUMN_CAPACITY, a.getCapacity());

		return db.insert(ACTIVITY_TABLE, null, cv) != -1;
	}

	public boolean addStudent(Student s) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(STUDENT_COLUMN_NAME, s.getName());
		cv.put(STUDENT_COLUMN_FAMILY_ID, s.getFamilyID());

		return db.insert(STUDENT_TABLE, null, cv) != -1;
	}*/

	/*public boolean addFamily(Family f) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(FAMILY_COLUMN_NAME, f.getName());
		cv.put(FAMILY_COLUMN_AMOUNT, f.getAmount());

		return db.insert(FAMILY_TABLE, null, cv) != -1;
	}*/

	/*public boolean deleteActivity(Activity a) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		String query = "DELETE FROM " + ACTIVITY_TABLE + " WHERE " + ACTIVITY_COLUMN_ID + " = " + a.getId();

		Cursor cursor = db.rawQuery(query, null);

		return cursor.moveToFirst();
	}*/

	/*public boolean deleteStudent(Student s) {
		SQLiteDatabase db = this.getWritableDatabase();

		String query = "DELETE FROM " + STUDENT_TABLE + " WHERE " + STUDENT_COLUMN_ID + " = " + s.getId();

		Cursor cursor = db.rawQuery(query, null);

		return cursor.moveToFirst();
	}*/

}
