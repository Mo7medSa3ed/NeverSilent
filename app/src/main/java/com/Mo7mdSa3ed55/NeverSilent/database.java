package com.Mo7mdSa3ed55.NeverSilent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {

    public static final String dbname = "Data";

    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Users ( id INTEGER PRIMARY KEY AUTOINCREMENT ,PhoneNumber TEXT  ,Name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public  Boolean insert (String PhoneNumber,String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PhoneNumber",PhoneNumber);
        contentValues.put("Name",Name);

        Long result = db.insert("Users",null,contentValues);
        if (result== -1)
            return false;
        else
            return true;
    }

    public ArrayList getAllDataName(){
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select Name from Users",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            String t1 = res.getString(0);

            arrayList.add(t1);
            res.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getAllDataNumber(){
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select PhoneNumber from Users",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            String t2 = res.getString(0);
            arrayList.add(t2);
            res.moveToNext();
        }
        return arrayList;
    }

    public void Delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Users", null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "Users" + "'");
    }
    public Integer Delete_id(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Users", "PhoneNumber = ?", new String[]{name});
    }

    public Boolean isEmpty(){
        boolean empty = true;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM Users", null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }
        cur.close();

        return empty;
    }

}
