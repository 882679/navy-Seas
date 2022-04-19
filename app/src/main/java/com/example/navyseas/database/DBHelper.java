package com.example.navyseas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.navyseas.database.Model.Activity;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

	public static final String ACTIVITY_TABLE = "ACTIVITY";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_DAY = "DAY";
	public static final String COLUMN_PRICE = "PRICE";
	public static final String COLUMN_CAPACITY = "CAPACITY";
	public static final String COLUMN_ID = "ID";

	public DBHelper(@Nullable Context context) {
		super(context, "Activity.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable = "CREATE TABLE " + ACTIVITY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_DAY + " TEXT, " + COLUMN_PRICE + " REAL, " + COLUMN_CAPACITY + " INTEGER)";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	public boolean addData(Activity a) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_NAME, a.getName());
		cv.put(COLUMN_DAY, a.getDay());
		cv.put(COLUMN_PRICE, a.getPrice());
		cv.put(COLUMN_CAPACITY, a.getCapacity());

		return db.insert(ACTIVITY_TABLE, null, cv) != -1;
	}

	public List<Activity> getData() {
		List<Activity> activities = new ArrayList<>();

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
			} while(cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return activities;
	}
}
