package com.snkz.dongocanh_ktra2_bai2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLExamHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LichThi.db";
    private static final int DATABASE_VERSION = 1;
    private static final String column_id = "id";
    private static final String column_name = "name";
    private static final String column_date = "date";
    private static final String column_time = "time";
    private static final String column_status = "status";
    public SQLExamHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE exams ("
                +column_id+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +column_name+ " TEXT, "
                +column_date+ " TEXT, "
                +column_time+ " TEXT, "
                +column_status+ " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(Exam exam){
        String query = "INSERT INTO exams"
                + "(" +column_name+ ", " +column_date+ ", "+column_time+ ", "+column_status+ ") "
                + " VALUES (?, ?, ? , ?)";
        String[] args = {exam.getName(), exam.getDate(), exam.getTime(), exam.getStatus()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(query, args);
    }

    public ArrayList<Exam> show(){
        ArrayList<Exam> arrExam = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("exams", null, null,null,null,null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String name = resultSet.getString(1);
            String date = resultSet.getString(2);
            String time = resultSet.getString(3);
            String status = resultSet.getString(4);

            arrExam.add(new Exam(id,name, date, time, status));
        }
        resultSet.close();
        return arrExam;
    }

    public void edit(Exam exam){
        SQLiteDatabase statement = getWritableDatabase();
        String query = "UPDATE exams SET "
                +column_name+ " = '" +exam.getName()+ "', "
                +column_date+ " = '" +exam.getDate()+ "', "
                +column_time+ " = '" +exam.getTime()+ "', "
                +column_status+ " = '" +exam.getStatus()+ "' "
                +"WHERE id = " +exam.getId();
        statement.execSQL(query);
    }

    public int delete(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("exams", whereClause, whereArgs);
    }

    public ArrayList<Exam> getExamByName(String name){
        ArrayList<Exam> arrTime = new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" +name+ "%"};
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("exams",null,whereClause,whereArgs,null,null,null,null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(column_id));
            String nameCourse = cursor.getString(cursor.getColumnIndex(column_name));
            String date = cursor.getString(cursor.getColumnIndex(column_date));
            String time = cursor.getString(cursor.getColumnIndex(column_time));
            String status = cursor.getString(cursor.getColumnIndex(column_status));
            arrTime.add(new Exam(id, nameCourse, date, time, status));
        }
        cursor.close();
        return arrTime;
    }

    public int getNumberWriting(){
        int count = 0;
        String whereClause = "status LIKE ?";
        String[] whereArgs = {"Writing"};
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("exams",  null, whereClause,whereArgs,null,null,null,null);
        while(cursor!=null && cursor.moveToNext()){
            count++;
        }
        return count;
    }

}
