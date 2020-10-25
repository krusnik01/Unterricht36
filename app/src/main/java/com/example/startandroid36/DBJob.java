package com.example.startandroid36;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBJob {
    private SQLiteDatabase db;

    public DBJob(Context context) {
        db = new DBHelper(context).getWritableDatabase();
    }
    private DBHelper DbData;


    public void Insert(ContentValues cv) {
           db.insert(DbData.DATABASE_NAME, null, cv);
    }

    public void DeleteALL() {
        db.delete(DbData.DATABASE_NAME,null,null);
    }


    public ArrayList<String> getData() {
        ArrayList<String > list = new ArrayList<>();

        Cursor cursor =db.query(DbData.DATABASE_NAME, null, null , null,null,null,null);
        if ((cursor != null)&&(cursor.getCount()>0)) {
            cursor.moveToFirst();
            int idColIndex = cursor.getColumnIndex("id");  // определяем номера столбцов по имени в выборке
            int nameColIndex = cursor.getColumnIndex("name");
            int peopleColIndex = cursor.getColumnIndex("people");
            int regionColIndex = cursor.getColumnIndex("region");
            do {
                list.add("ID = " + cursor.getInt(idColIndex) +    // получаем значения по номерам столбцов
                        ", name = " + cursor.getString(nameColIndex) +
                        ", people = " + cursor.getString(peopleColIndex)+", region = " + cursor.getString(regionColIndex)+'\n');
            } while (cursor.moveToNext());
        } else list.add("База пуста");
        return list;
    }


    public ArrayList<String > getQuery(String[] columns, String selection,String[] selectionArgs,String groupBy, String having, String orderBy){
        //Вывод записей с разнообразным отбором
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor =db.query(DbData.DATABASE_NAME, columns, selection , selectionArgs,groupBy,having,orderBy);//ищем где where
        if ((cursor != null)&&(cursor.getCount()>0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return list;
    }
}
