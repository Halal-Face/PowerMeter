package com.halal_face.powermeter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class PowerDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";
    private static final String COL3 = "POWER";
    private static final String COL2 = "DATE";
    private static final String COL1 = "ID";
    private  final String TABLE_NAME;
    private Context context;

    public PowerDbHelper(Context context, String table_name) {
        super(context, table_name, null, 1);
        TABLE_NAME = table_name;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 +" INT, "
                + COL3 +" INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(int item, int date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +" WHERE " + COL2 + " = '" + date + "'";
        Cursor data = db.rawQuery(query, null);


        if(data.moveToNext()){
            Cursor updateData = getItemID(date);
            int itemID = -1;
            if(updateData.moveToNext()){
                itemID = updateData.getInt(0);
                int oldData = updateData.getInt(2);
                int newData = oldData + item;
                updateItem(newData, itemID, oldData);
            }
            if(itemID == -1){
                return false;
            } else{
                return true;
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, date);
        contentValues.put(COL3, item);
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

    public Cursor getItemID(int date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + date + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateItem(int newItem, int id, int oldItem){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newItem +"' WHERE " + COL1 + " = '" +
                id + "' AND " + COL3 + " = '" + oldItem + "'";
        db.execSQL(query);
    }
    public void deleteItem(int id, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL1 + " = '" + id +"'" + " AND " + COL2 +
                " = '" + item + "'";
        db.execSQL(query);
        Log.d(TAG, "QUERY DELETE: " + query);
    }

}
