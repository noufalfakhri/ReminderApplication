package com.example.reminderapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Tasks.db";
    public static final String TABLE_NAME = "task_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "TITLE";
    public static final String col_3 = "TIME";
    public static final String col_4 = "DATE";
    public static final String col_5 = "IMPORTANCE";

    public DatabaseHelper(@Nullable Context context) {
        super(context ,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL
                ("create table " + TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " TITLE TEXT," +
                        " TIME TEXT," + " DATE TEXT," +
                        " IMPORTANCE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTask (String Title, String Time, String Date, int Importance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, Title);
        contentValues.put(col_3, Time);
        contentValues.put(col_4, Date);
        contentValues.put(col_5, Importance);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1) return false;
        else return true;
    }


    public boolean updateTask (Integer id, String Title, String Time, String Date, int Importance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, Title);
        contentValues.put(col_3, Time);
        contentValues.put(col_4, Date);
        contentValues.put(col_5, Importance);

        db.update(TABLE_NAME, contentValues, "id = ?",new String[] { Integer.toString(id) } );
        return true;
    }


    public Cursor retrieveTask (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+ "where id="+id,null);
        return  res;
    }

    public Cursor retrieveAllTasks (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }

    public Integer deleteTask (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[] { Integer.toString(id) });
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
    public ArrayList<String> getAllTasks() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(col_2)));
            res.moveToNext();
        }
        return array_list;
    }
}

