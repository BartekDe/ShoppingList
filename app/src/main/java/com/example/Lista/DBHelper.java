package com.example.Lista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping";
    private static final int DB_VER = 3;
    public static final String DB_TABLE = "items";
    public static final String DB_COLUMN_NAME = "name";
    public static final String DB_COLUMN_DESC = "description";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL," +
                "%s TEXT);", DB_TABLE, DB_COLUMN_NAME, DB_COLUMN_DESC);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DROP TABLE IF EXISTS %s;", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_NAME, item);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN_NAME + " = ?", new String[]{item});
        db.close();
    }

    public ArrayList<String> getItemsList() {
        ArrayList<String> itemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN_NAME}, null, null, null, null, null);
        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DB_COLUMN_NAME);
            itemsList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return itemsList;
    }

    public String getDescription(String name) {
        String result = "domyslny opis";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DB_COLUMN_DESC + " FROM " + DB_TABLE + " WHERE " + DB_COLUMN_NAME + "=?", new String[] {name});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DESC)); // pobiera nulla
        }
        cursor.close();
        db.close();
        return result;
    }

    public void saveDescription(String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s = '%s';", DB_TABLE, DB_COLUMN_DESC, desc, DB_COLUMN_NAME, name);
        db.execSQL(query);
    }
}
