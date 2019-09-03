package com.example.akash.fooddiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by akash on 12/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //mAge,mGenderId,mWeight,mHight,mBdyfat,bmi,resultBmr,resultTDEE,leanmass,fatmass
    public static final String DATABASE_NAME="History2.db";
    public static final String TABLE_NAME="history_table";
    public static final String ID="ID";
    public static final String[] COL={"DATE","TIME","AGE","GENDER","WEIGHT","HEIGHT","BODY_FAT","BMI","BMR","TDEE","LEANMASS","FATMASS"};






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",DATE TEXT,TIME TEXT,AGE REAL,GENDER REAL,WEIGHT REAL,HEIGHT REAL," +
                "BODY_FAT REAL,BMI REAL,BMR REAL,TDEE REAL,LEANMASS REAL,FATMASS REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String date,String time,double[] historydata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL[0],date);
        contentValues.put(COL[1],time);
        for(int i=0;i<historydata.length;i++){
            contentValues.put(COL[i+2],historydata[i]);
        }
        double result=db.insert(TABLE_NAME,null,contentValues);
        if(result!=-1){
            Log.d("test","inserted");
        }
        else{
            Log.d("test","not inserted");
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
