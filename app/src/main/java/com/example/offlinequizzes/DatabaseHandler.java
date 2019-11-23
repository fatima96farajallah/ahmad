package com.example.offlinequizzes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database version
    private static final int   DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "exams.db";

    //Table name
    private static final String TABLE_NAME = "questions";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXAM_NAME = "exam_name";
    private static final String COLUMN_EXAM_PART = "exam_part";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    private static final String COLUMN_ANSWER_ONE ="answer_one";
    private static final String COLUMN_ANSWER_TWO ="answer_two";
    private static final String COLUMN_ANSWER_THREE ="answer_three";
    private static final String COLUMN_ANSWER_FOUR ="answer_four";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,exam_name TEXT,exam_part TEXT,question TEXT,correct_answer TEXT,answer_one TEXT,answer_two TEXT,answer_three TEXT,answer_four TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String exam_name, String exam_part,String question,String correct_answer,
                           String answer_one, String answer_two,String answer_three,String answer_four){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXAM_NAME, exam_name);
        contentValues.put(COLUMN_EXAM_PART,exam_part);
        contentValues.put(COLUMN_QUESTION,question);
        contentValues.put(COLUMN_CORRECT_ANSWER,correct_answer);
        contentValues.put(COLUMN_ANSWER_ONE,answer_one);
        contentValues.put(COLUMN_ANSWER_TWO,answer_two);
        contentValues.put(COLUMN_ANSWER_THREE,answer_three);
        contentValues.put(COLUMN_ANSWER_FOUR,answer_four);

        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        long result=  sqLiteDB.insert(TABLE_NAME, null,contentValues);
        sqLiteDB.close();
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"",null);
    }

}
