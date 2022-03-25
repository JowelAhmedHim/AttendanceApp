package com.example.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.attendance.Constant.*;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context,Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table SQLite command
        db.execSQL(Constant.CREATE_CLASS_TABLE);
        db.execSQL(Constant.CREATE_STUDENT_TABLE);
        db.execSQL(Constant.CREATE_STATUS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(Constant.DROP_CLASS_TABLE);
                db.execSQL(Constant.DROP_STUDENT_TABLE);
                db.execSQL(Constant.DROP_STATUS_TABLE);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    long addStudent(long cid,int roll,String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.C_ID,cid);
        contentValues.put(Constant.STUDENT_ROLL_KEY,roll);
        contentValues.put(Constant.STUDENT_NAME_KEY,name);
        return database.insert(Constant.STUDENT_TABLE_NAME,null,contentValues);
    }

    Cursor getStudentTable(long cid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(Constant.STUDENT_TABLE_NAME,null,Constant.C_ID +"=?",new String[]{String.valueOf(cid)},null,null,null);
    }

    long updateStudent(long sid,String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.STUDENT_NAME_KEY,name);
        return database.update(Constant.STUDENT_TABLE_NAME,values,sid + "=?",new String[]{String.valueOf(sid)});
    }

    int deleteStudent(long sid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(Constant.STUDENT_TABLE_NAME,Constant.S_ID + "=?",new String[]{String.valueOf(sid)});
    }


    long addClass(String className,String subjectName){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.CLASS_NAME_KEY,className);
        values.put(Constant.SUBJECT_NAME_KEY,subjectName);

        long id = database.insert(Constant.CLASS_TABLE_NAME,null,values);
        return id;
    }

    long updateClass(long cid,String className,String subjectName){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.CLASS_NAME_KEY,className);
        values.put(Constant.SUBJECT_NAME_KEY,subjectName);

        long id = database.update(Constant.CLASS_TABLE_NAME,values,Constant.C_ID+"=?",new String[]{String.valueOf(cid)});
        return id;
    }



    // read class table
    Cursor getClassTable(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(Constant.SELECT_CLASS_TABLE,null);
    }



    int deleteClass( long cid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(Constant.CLASS_TABLE_NAME,Constant.C_ID + "=?",new String[]{String.valueOf(cid)});
    }


    long addStatus(long sid,long cid,String date,String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.S_ID,sid);
        contentValues.put(Constant.C_ID,cid);
        contentValues.put(Constant.DATE_KEY,date);
        contentValues.put(Constant.STATUS_KEY,status);
        return database.insert(Constant.STATUS_TABLE_NAME,null,contentValues);
    }

    long updateStatus(long sid,String date,String status){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.STATUS_KEY,status);
        String whereClause = Constant.DATE_KEY +"='"+date + "' AND "+ Constant.S_ID + " = " +sid;
        return database.update(Constant.STATUS_TABLE_NAME,contentValues,whereClause,null);
    }

    String getStatus(long sid,String date){
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = Constant.DATE_KEY +"='"+date + "' AND "+ Constant.S_ID + " = " +sid;
        Cursor cursor = database.query(Constant.STATUS_TABLE_NAME,null,whereClause,null,null,null,null);
        if (cursor.moveToFirst()){
            status = cursor.getString(cursor.getColumnIndexOrThrow(Constant.STATUS_KEY));
        }
        return status;

    }

    Cursor getDistinctMonths(long cid){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(Constant.STATUS_TABLE_NAME,new String[]{Constant.DATE_KEY},Constant.C_ID + "="+cid,null,"substr("+Constant.DATE_KEY + " , 4.7)",null,null);

    }


}
