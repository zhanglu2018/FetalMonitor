package com.zl.administrator.fetalmonitor.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Administrator on 2018/4/14/014.
 */

public class DBHandle {

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;


    public int login(String id, String pwd ,Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM user WHERE UserID =? and  Password =?",
               new String[]{id,pwd});
        //存在数据才返回true
        if( cursor.moveToFirst())
        {
            cursor.close();
            return 1;
        }
        cursor.close();
        return 0;
    }
    public void adduser(User user, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        db.execSQL("INSERT INTO user(UserID,Password,DueDate) values(?,?.?)",
                 new String[]{user.getUserID(),user.getPassword(),user.getDueDate()});
        db.close();
    }

    public User finduser(String id,Context context)
    {

        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM user WHERE UserID =? ",
                new String[]{id});
        //存在数据才返回true
        if( cursor.moveToFirst())
        {
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            String Password = cursor.getString(cursor.getColumnIndex("Password"));
            int Height = cursor.getInt(cursor.getColumnIndex("Height"));
            int Weight = cursor.getInt(cursor.getColumnIndex("Weight"));
            String DueDate =  cursor.getString(cursor.getColumnIndex("DueDate"));
            return new User(id,Name,Password,Height,Weight,DueDate);
        }
        cursor.close();
        return null;

    }

    public void updateuser(User user, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("update user set Name = ?, Height = ?,Weight = ?, Duedate = ?,Password = ?where UserID = ?  ",
                new Object[]{user.getName(),user.getHeight(),user.getWeight(),user.getDueDate(),user.getPassword(),user.getUserID()});
        db.close();
    }

    public Alarm findalarm(String id,Context context)
    {
       
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM alarm WHERE UserID =? ",
                new String[]{id});
        //存在数据才返回true
        if( cursor.moveToFirst())
        {
            int High = cursor.getInt(cursor.getColumnIndex("High"));
            int Low = cursor.getInt(cursor.getColumnIndex("Low"));
            int Switch = cursor.getInt(cursor.getColumnIndex("Switch"));
         
            return new Alarm(id,High,Low,Switch);
        }
        cursor.close();
        return null;

    }

    public void addalarm(Alarm alarm, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("insert into alarm  values(?,?,?,?)  ",
                new Object[]{ alarm.getUserID(),alarm.getHigh(),alarm.getLow(),alarm.getSwitch()});
        db.close();
    }

    public void updatealarm(Alarm alarm,Context context)
    {

        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("update alarm set High = ?,Low = ?,Switch =?  where UserID = ?  ",
                new Object[]{alarm.getHigh(),alarm.getLow(), alarm.getSwitch(),alarm.getUserID()});
        db.close();
    }

    public void addinfo(Info info, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("insert into info  values(?,?,?,?,?)  ",
                new Object[]{ info.getUserID(),info.getFHR(),info.getTOCO(),info.getAFM(),info.getTime()});
        db.close();
    }
    public List<Info> findinfo(String id, Context context)
    {
        List<Info> info = new ArrayList<Info>();
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM info WHERE UserID =? ",
                new String[]{id});
        //存在数据才返回true
        while(cursor.moveToNext())
        {
            String UserID = id;
            int FHR = cursor.getInt(cursor.getColumnIndex("FHR"));
            int TOCO = cursor.getInt(cursor.getColumnIndex("TOCO"));
            int AFM = cursor.getInt(cursor.getColumnIndex("AFM"));
            String Time = cursor.getString(cursor.getColumnIndex("Time"));
            info.add(new Info(UserID,FHR,TOCO,AFM,Time));

        }
        cursor.close();

        return info;
    }
}
