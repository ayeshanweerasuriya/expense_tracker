package com.s22010337.myexpensetrackerlab05;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

// Define a DatabaseHelper class that extends SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {
    // Define constants for database name, table name, and column names
    public static final String DATABASE_NAME = "ExpenseTracker.db";
        public static final String TABLE_NAME = "daily_expenses";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Date";
    public static final String COL_3 = "Category";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "WeekNumber";

    // Constructor for DatabaseHelper, takes a Context as parameter
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Override the onCreate method to create the database and tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + " Date TEXT, Category TEXT, Amount REAL, WeekNumber INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Recreate the table by calling onCreate
        onCreate(db);
    }

    public boolean insertData(String date, String category, double amount , int weekNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Put values into the ContentValues object
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, category);
        contentValues.put(COL_4, amount);
        contentValues.put(COL_5, weekNumber);

        // Insert the values into the database table
        long results = db.insert(TABLE_NAME, null, contentValues);
        // Check if the insertion was successful
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Execute the query to get all data from the table
        Cursor results = db.rawQuery("select * from " + TABLE_NAME, null);
        return results;
    }

    public boolean updateData(String id, String date, String category, double amount , int weekNumber ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Put updated values into the ContentValues object
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, category);
        contentValues.put(COL_4, amount);
        contentValues.put(COL_5, weekNumber);
        // Update the data in the database table based on the ID
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete data from the database table based on the ID
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
