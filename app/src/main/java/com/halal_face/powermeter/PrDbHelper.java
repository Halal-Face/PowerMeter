package com.halal_face.powermeter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PrDbHelper extends SQLiteOpenHelper {
    static final String TAG = "PR DataBaseHelper";
    private final String TABLE_NAME = "PR";
    private final String COL3 = "Rersonal_Record";
    private final String COL2 = "exercise_name";
    private final String COL1 = "ID";
    private Context context;

    public PrDbHelper(Context context) {
        super(context, "PR", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 +" INT, " + COL3 + " INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //used when adding new exercises to the table
    public boolean addData(String exerName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +" WHERE " + COL2 + " = '" + exerName + "'";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToNext()){
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, exerName);
        contentValues.put(COL3, 0);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return (result ==-1)? false :true;

    }
    //used when updating values
    public boolean addData(String exerName, int newRecord){
        if(newRecord < 0){
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +" WHERE " + COL2 + " = '" + exerName + "'";
        Cursor data = db.rawQuery(query, null);
        if(data.moveToNext()){
            String recordUpdateQuery = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = '" + newRecord +"' WHERE " + COL2 + " = '" + exerName + "'" ;
            db.execSQL(recordUpdateQuery);
            return true;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, exerName);
        contentValues.put(COL3, newRecord);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return (result ==-1)? false :true;

    }

    //return iterator for all the data
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String exerName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + exerName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean updateExerName(String oldName, String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryCheck = "SELECT " + COL2 + " FROM " + TABLE_NAME +" WHERE " + COL2 + " = '" + oldName + "'";
        Cursor data = db.rawQuery(queryCheck, null);
        if(data.moveToNext()){
            return false;
        }
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName +"' WHERE " + COL2 + " = '" + oldName + "'";
        db.execSQL(query);
        return true;
    }

    public void deleteItem(String exerName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL2 + " = '" + exerName +"'";
        db.execSQL(query);
    }

}
