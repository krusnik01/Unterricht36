package com.example.startandroid36;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public final String LOG_TAG = "myLogs";
    private static final int DATABASE_VERSION= 2;
    public  static final String DATABASE_NAME ="mytable3";

    public  static final String DATABASE_COLUMN_NAME ="name";
    public  static final String DATABASE_COLUMN_PEOPLE ="people";
    public  static final String DATABASE_COLUMN_REGION ="region";


    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("CREATE TABLE "+ DATABASE_NAME +" (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATABASE_COLUMN_NAME+" TEXT NOT NULL,"
                + DATABASE_COLUMN_PEOPLE+" TEXT NOT NULL,"
                + DATABASE_COLUMN_REGION+" TEXT NOT NULL);");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
