package com.halal_face.powermeter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PowerDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";
    private static final String COL2 = "POWER";
    private static final String COL1 = "ID";
    private  final String TABLE_NAME;
    private Context context;

    public PowerDbHelper(Context context, String name) {
        super(context, name, null, 1);
        TABLE_NAME = name;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 +" INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(int item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return (result ==-1)? false :true;

    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + item + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateItem(int newItem, int id, int oldItem){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newItem +"' WHERE " + COL1 + " = '" +
                id + "' AND " + COL2 + " = '" + oldItem + "'";
        db.execSQL(query);
    }
    public void deleteItem(int id, int item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL1 + " = '" + id +"'" + " AND " + COL2 +
                " = '" + item + "'";
        db.execSQL(query);
        Log.d(TAG, "QUERY DELETE: " + query);
    }

    public ArrayList<Integer> getXData(){
        ArrayList<Integer> xNewData = new ArrayList<Integer>();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            xNewData.add(cursor.getInt(cursor.getColumnIndex(COL1)));
        }
        cursor.close();
        return xNewData;
    }
    public ArrayList<Integer> getYData(){
        ArrayList<Integer> yNewData = new ArrayList<>();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            yNewData.add(cursor.getInt(cursor.getColumnIndex(COL2)));
        }
        cursor.close();
        return yNewData;
    }

    public void updateDbName(String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        PowerDbHelper newDb = new PowerDbHelper(context, newName);
//        String query ="INSERT INTO " + newName + " SELECT * FROM " + TABLE_NAME;
//        db.execSQL(query);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
//        //get iterator for data
//        Cursor data = mMasterDbHelper.getData();
//        //add the data from to the arraylsit
//        ArrayList<String> listData = new ArrayList<>();
//        while(data.moveToNext()){
//            listData.add(data.getString(1));
//        }
        while(data.moveToNext()){
            newDb.addData(data.getInt(1));
        }
    }

}
