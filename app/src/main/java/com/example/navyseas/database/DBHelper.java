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
    // Activity Table
    public static final String ACTIVITY_TABLE = "ACTIVITY";
    public static final String ACTIVITY_COLUMN_NAME = "NAME";
    public static final String ACTIVITY_COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String ACTIVITY_COLUMN_DAY = "DAY";
    public static final String ACTIVITY_COLUMN_PRICE = "PRICE";
    public static final String ACTIVITY_COLUMN_CAPACITY = "CAPACITY";
    public static final String ACTIVITY_COLUMN_ID = "ID";

    // Family Table
    public static final String FAMILY_TABLE = "FAMILY";
    public static final String FAMILY_COLUMN_NAME = "NAME";
    public static final String FAMILY_COLUMN_EMAIL = "EMAIL";
    public static final String FAMILY_COLUMN_PASSWORD = "PASSWORD";
    public static final String FAMILY_COLUMN_ID = "ID";

    // Student Table
    public static final String STUDENT_TABLE = "STUDENT";
    public static final String STUDENT_COLUMN_NAME = "NAME";
    public static final String STUDENT_COLUMN_FAMILY_ID = "FAMILY_ID";
    public static final String STUDENT_COLUMN_ID = "ID";

    // Reservation Table
    public static final String RESERVATION_TABLE = "RESERVATION";
    public static final String RESERVATION_COLUMN_STUDENT_ID = "STUDENT_ID";
    public static final String RESERVATION_COLUMN_ACTIVITY_ID = "ACTIVITY_ID";

    public DBHelper(@Nullable Context context) {
        super(context, "Navy-SEAS.db", null, 1);
    }

    /**
     * If the local DB doesn't exists, it creates a new file, then DB structure creation begins. <br>
     * 4 tables are created:<br>
     * - {@link Activity}:<br>
     * Columns: {@code id, name, day, price, capacity;}<br>
     * - {@link Family}:<br>
     * Columns: {@code id, name, familyID;}<br>
     * - {@link Student}:<br>
     * Columns: {@code id, name, familyID;}<br>
     * - {@link Reservation}:<br>
     * Columns: {@code studentID, activityID;}<br>
     * Description: N:M relationship-table between {@link Student} and {@link Activity}.<br>
     * In the end, every table gets populated by mock data.
     * Queries as "INSERT INTO" are used for this purpose.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + ACTIVITY_TABLE + " (" +
                ACTIVITY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACTIVITY_COLUMN_NAME + " TEXT, " +
                ACTIVITY_COLUMN_DESCRIPTION + " TEXT, " +
                ACTIVITY_COLUMN_DAY + " TEXT, " +
                ACTIVITY_COLUMN_PRICE + " REAL, " +
                ACTIVITY_COLUMN_CAPACITY + " INTEGER" + ")"
        );

        database.execSQL("CREATE TABLE " + FAMILY_TABLE + " (" +
                FAMILY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FAMILY_COLUMN_NAME + " TEXT, " +
                FAMILY_COLUMN_EMAIL + " TEXT UNIQUE, " +
                FAMILY_COLUMN_PASSWORD + " TEXT)"
        );

        database.execSQL("CREATE TABLE " + STUDENT_TABLE + " (" +
                STUDENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STUDENT_COLUMN_NAME + " TEXT, " +
                STUDENT_COLUMN_FAMILY_ID + " INTEGER, " +
                "CONSTRAINT FAMILY_FK FOREIGN KEY (" + STUDENT_COLUMN_FAMILY_ID + ")" +
                "REFERENCES " + FAMILY_TABLE + " (" + FAMILY_COLUMN_ID + "))"
        );

        database.execSQL("CREATE TABLE " + RESERVATION_TABLE + " (" +
                RESERVATION_COLUMN_STUDENT_ID + " INTEGER, " +
                RESERVATION_COLUMN_ACTIVITY_ID + " INTEGER, " +
                "CONSTRAINT RESERVATION_PK PRIMARY KEY (" +
                RESERVATION_COLUMN_STUDENT_ID + ", " + RESERVATION_COLUMN_ACTIVITY_ID + "), " +
                "CONSTRAINT STUDENT_FK FOREIGN KEY (" + RESERVATION_COLUMN_STUDENT_ID + ") " +
                "REFERENCES " + STUDENT_TABLE + " (" + STUDENT_COLUMN_ID + "), " +
                "CONSTRAINT ACTIVITY_FK FOREIGN KEY (" + RESERVATION_COLUMN_ACTIVITY_ID + ") " +
                "REFERENCES " + ACTIVITY_TABLE + " (" + ACTIVITY_COLUMN_ID + "));"
        );

        database.execSQL("INSERT INTO " + ACTIVITY_TABLE +
                " (" + ACTIVITY_COLUMN_NAME + ", " + ACTIVITY_COLUMN_DESCRIPTION + ", " +
                ACTIVITY_COLUMN_DAY + ", " + ACTIVITY_COLUMN_PRICE + ", " +
                ACTIVITY_COLUMN_CAPACITY + ")" +
                " VALUES " +
                "('Scacchi', 'I bambini passano il pomeriggio scoprendo la bellezza e la profondità del gioco degli scacchi.\n\nImparano strategie utili nella vita allenando la mente.' ,'Lunedi', 8, 20), " +
                "('Lettura', 'I bambini passano il pomeriggio leggendo i libri della biblioteca della scuola a scelta.\n\nDisponibili grandi classici adatti a tutte le età.' ,'Martedi', 7, 30), " +
                "('Disegno', 'I bambini possono esprimere la loro creatività in un laboratorio di arte e disegno con lavoretti di disegno, pittura e decoupage.\n\n(Materiale richiesto comunicato durante le ore di arte)' ,'Mercoledi', 5, 10), " +
                "('Pallavolo', 'I bambini possono divertirsi e fare attività fisica con movimento e gioco di squadra.\n\nLe partite di pallavolo vengono seguite da un insegnante presente.' ,'Giovedi', 10, 50), " +
                "('Calcio', 'I bambini possono divertirsi e fare attività fisica con movimento e gioco di squadra.\n\nLe partite di calcio vengono seguite da un insegnante presente.' ,'Venerdi', 15, 20), " +
                "('Aiuto compiti', 'I bambini vengono seguiti ed aiutati da gli alunni della scuola media.\n\nÈ possibile richiedere lezioni private o aiuto da parte degli insegnanti.' ,'Lunedi', 10, 60)"
        );

        database.execSQL(
                "INSERT INTO " + FAMILY_TABLE + " (" + FAMILY_COLUMN_NAME + ", " +
                        FAMILY_COLUMN_EMAIL + ", " + FAMILY_COLUMN_PASSWORD + ")" +
                        " VALUES ('Navy family', 'Navy@stud.unive.it', 'Navy'), ('Seas family', 'Seas@stud.unive.it', 'Seas')"
        );

        database.execSQL("INSERT INTO " + STUDENT_TABLE +
                " (" + STUDENT_COLUMN_NAME + ", " + STUDENT_COLUMN_FAMILY_ID + ")" +
                " VALUES " + "('Angelo', 1), " + "('Giulia', 1), " + "('Alvise', 1), " +
                "('Alessandro', 1), " + "('Ivan', 2)");

        database.execSQL("INSERT INTO " + RESERVATION_TABLE +
                " VALUES (1, 1), (1, 2), (2, 2), (2, 1), (5, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Family login(String email, String password) {
        Family family = null;

        String query = "SELECT * FROM " + FAMILY_TABLE +
                " WHERE " + FAMILY_COLUMN_EMAIL + " = '" + email +
                "' AND " + FAMILY_COLUMN_PASSWORD + " = '" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                family = new Family(id, name, email, password);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return family;
    }

    /**
     * Subscribes a Student to an Activity by creating and inserting a {@link Reservation}
     * Object on the database.
     *
     * @param reservation Object that is a contract between a {@link Student} and an {@link Activity}
     */
    public void subscribe(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RESERVATION_COLUMN_STUDENT_ID, reservation.getStudentID());
        cv.put(RESERVATION_COLUMN_ACTIVITY_ID, reservation.getActivityID());
        db.insert(RESERVATION_TABLE, null, cv);
        db.close();
    }

    /**
     * Unsubscribes a {@link Student} from an {@link Activity} by deleting a given
     * {@link Reservation} Object from the database.
     *
     * @param reservation Object that is a contract between a {@link Student} and an {@link Activity}
     */
    public void unsubscribe(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + RESERVATION_TABLE +
                " WHERE " + RESERVATION_COLUMN_STUDENT_ID + " = " + reservation.getStudentID() +
                " AND " + RESERVATION_COLUMN_ACTIVITY_ID + " = " + reservation.getActivityID();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    /**
     * @return list of all families that have been recorded by the school
     */
    public ArrayList<Family> getFamilies() {
        ArrayList<Family> families = new ArrayList<>();

        String query = "SELECT * FROM " + FAMILY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);

                families.add(new Family(id, name, email, password));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return families;
    }

    /**
     * @return list of children of a given {@link Family}.
     */
    public ArrayList<Student> getChildren(Family family) {
        ArrayList<Student> children = new ArrayList<>();

        String query = "SELECT * FROM " + STUDENT_TABLE +
                " WHERE " + STUDENT_COLUMN_FAMILY_ID + " = " + family.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int familyID = cursor.getInt(2);

                children.add(new Student(id, name, familyID));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return children;
    }

    /**
     * @return list of all activities that have been recorded by the school
     */
    public ArrayList<Activity> getActivities() {
        ArrayList<Activity> activities = new ArrayList<>();

        String query = "SELECT * FROM " + ACTIVITY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String day = cursor.getString(3);
                double price = cursor.getDouble(4);
                int capacity = cursor.getInt(5);

                activities.add(new Activity(id, name, description, day, price, capacity));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return activities;
    }

    /**
     * @return list of all the activities a given {@link Student} is subscribed to.
     */
    public ArrayList<Activity> getActivities(Student student) {
        ArrayList<Reservation> reservations = this.getReservations(student);
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

    /**
     * @return get an activity by its ID.
     */
    public Activity getActivityByID(int id) {
        Activity a;
        String query = "" +
                "SELECT *" +
                "FROM " + ACTIVITY_TABLE + " a " +
                "WHERE " + ACTIVITY_COLUMN_ID + "=" + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int activityID = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            String day = cursor.getString(3);
            double price = cursor.getDouble(4);
            int capacity = cursor.getInt(5);

            cursor.close();
            db.close();
            a = new Activity(activityID, name, description, day, price, capacity);
        } else a = new Activity();

        cursor.close();
        db.close();
        return a;
    }

    /**
     * @return sum of all activities prices of a given {@link Family}'s children.
     * It's the amount that the {@link Family} owes to the school.
     */
    public int getAmount(Family family) {
        int amount = 0;

        String query = "" +
                "SELECT SUM(" + ACTIVITY_COLUMN_PRICE + ")" +
                "FROM " + ACTIVITY_TABLE + " a " +
                "INNER JOIN " + RESERVATION_TABLE + " r " +
                "ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID + " " +
                "INNER JOIN " + STUDENT_TABLE + " s " +
                "ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID + " " +
                "INNER JOIN " + FAMILY_TABLE + " f " +
                "ON s." + STUDENT_COLUMN_FAMILY_ID + " = f." + FAMILY_COLUMN_ID + " " +
                "AND s." + STUDENT_COLUMN_FAMILY_ID + " = " + family.getId() + " " +
                "GROUP BY s." + STUDENT_COLUMN_ID + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                amount += cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return amount;
    }

    /**
     * @return sum of all activities prices of a given {@link Student}
     */
    public int getAmount(Student student) {
        int amount = 0;

        String query = "" +
                "SELECT SUM(" + ACTIVITY_COLUMN_PRICE + ")" +
                "FROM " + ACTIVITY_TABLE + " a" +
                " INNER JOIN " + RESERVATION_TABLE + " r " +
                "ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID +
                " INNER JOIN " + STUDENT_TABLE + " s " +
                "ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID +
                " INNER JOIN " + FAMILY_TABLE + " f " +
                "ON s." + STUDENT_COLUMN_FAMILY_ID + " = f." + FAMILY_COLUMN_ID +
                " AND s." + STUDENT_COLUMN_ID + " = " + student.getId() +
                " GROUP BY s." + STUDENT_COLUMN_ID + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                amount += cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return amount;
    }

    /**
     * @return list of all given {@link Family}'s children reservations.
     */
    public ArrayList<Reservation> getReservations(Family family) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        String query = "SELECT * FROM " + RESERVATION_TABLE + " r " +
                " INNER JOIN " + STUDENT_TABLE + " s" +
                " ON s." + STUDENT_COLUMN_ID + " = r." + RESERVATION_COLUMN_STUDENT_ID +
                " INNER JOIN " + FAMILY_TABLE + " f" +
                " ON f." + FAMILY_COLUMN_ID + " = s." + STUDENT_COLUMN_FAMILY_ID +
                " AND f." + FAMILY_COLUMN_ID + " = " + family.getId();

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

    /**
     * @return list of all reservations concerning a given {@link Activity}.
     */
    public ArrayList<Reservation> getReservations(Activity activity) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        String query = "SELECT * FROM " + RESERVATION_TABLE + " r " +
                "INNER JOIN " + ACTIVITY_TABLE + " a " +
                "ON a." + ACTIVITY_COLUMN_ID + " = r." + RESERVATION_COLUMN_ACTIVITY_ID +
                " AND a." + ACTIVITY_COLUMN_ID + " = " + activity.getId();

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

    /**
     * @return list of all given {@link Student}'s reservations.
     */
    public ArrayList<Reservation> getReservations(Student student) {
        ArrayList<Reservation> reservations = new ArrayList<>();

        String query = "SELECT * FROM " + RESERVATION_TABLE +
                " WHERE " + RESERVATION_COLUMN_STUDENT_ID + " = " + student.getId();

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

    /**
     * @return list of all given {@link Student}'s reservations.
     */
    public int getNumberOfReservations(Activity activity) {

        String query = "SELECT COUNT(" + RESERVATION_COLUMN_ACTIVITY_ID + ") FROM " + RESERVATION_TABLE +
                " WHERE " + RESERVATION_COLUMN_ACTIVITY_ID + " = " + activity.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(0);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count;
    }

    /**
     * Checks if a given {@link com.example.navyseas.database.Model.Student} can subscribe
     * to a specific {@link com.example.navyseas.database.Model.Activity}.
     *
     * @param student  the student to check
     * @param activity the activity to check
     * @return true if "student" can subscribe to given activity, false otherwise.
     */
    public boolean checkActivity(Student student, Activity activity) {
        return !isStudentAlreadySubscribed(student, activity) &&
                !isStudentBusy(student, activity) &&
                checkIfActivityIsAvailable(activity) &&
                checkWeeklyActivitiesCap(student);
    }

    /**
     * Checks if a specific {@link com.example.navyseas.database.Model.Student} is already
     * subscribed to an another {@link com.example.navyseas.database.Model.Activity} occurring
     * on the same day.
     *
     * @param student  the student to check
     * @param activity the activity to check
     * @return true if "student" is already subscribed to another activity, false otherwise.
     */
    private boolean isStudentBusy(Student student, Activity activity) {
        for (int i = 0; i < this.getActivities(student).size(); i++) {
            if (this.getActivities(student).get(i).getDay().equals(activity.getDay()))
                return true;
        }

        return false;
    }

    /**
     * Checks if a specific {@link Student} is already subscribed to a specific {@link Activity}.
     *
     * @param student  the student to check
     * @param activity the activity to check
     * @return true if "student" is already subscribed to another activity, false otherwise.
     */
    public boolean isStudentAlreadySubscribed(Student student, Activity activity) {
        for (int i = 0; i < this.getActivities(student).size(); i++) {
            if (this.getActivities(student).get(i).getId() == activity.getId()) return true;
        }

        return false;
    }

    /**
     * Checks if given {@link Activity} has at least 1 "place" left.
     *
     * @param activity the activity to check
     * @return true if "activity" can be booked, false if there are no places left.
     */
    private boolean checkIfActivityIsAvailable(Activity activity) {
        return this.getReservations(activity).size() < activity.getCapacity();
    }

    /**
     * Checks if given {@link Student}'s booked for 4 activities at most.
     *
     * @param student the student to check
     * @return true if "activity" can be booked, false if there are no places left.
     */
    private boolean checkWeeklyActivitiesCap(Student student) {
        return this.getReservations(student).size() < 5;
    }

    public ArrayList<Activity> getActivities(ArrayList<Reservation> reservations) {
        ArrayList<Activity> activities = getActivities();
        ArrayList<Activity> familyActivities = new ArrayList<>();

        for (Activity a : activities) {
            for (Reservation r : reservations) {
                if (a.getId() == r.getActivityID()) familyActivities.add(a);
            }
        }

        return familyActivities;
    }
}
