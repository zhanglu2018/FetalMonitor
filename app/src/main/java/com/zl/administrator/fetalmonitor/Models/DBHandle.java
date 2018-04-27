package com.zl.administrator.fetalmonitor.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.*;
import java.util.Date;
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
    public void adduser(String id, String pwd , Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        db.execSQL("INSERT INTO user(UserID,Password) values(?,?)",
                 new String[]{id,pwd});
        db.close();
    }

    public User finduser(String id,Context context)
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition parsePosition  = new ParsePosition(8);
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
            String time = cursor.getString(cursor.getColumnIndex("DueDate"));
            Date DueDate = format1.parse(time,parsePosition);
            return new User(id,Name,Password,Height,Weight,DueDate);
        }
        cursor.close();
        return null;

    }

    public void updateuser(User user, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("update user set Name = ?, Height = ?,Weight = ? where UserID = ?  ",
                new Object[]{user.getName(),user.getHeight(),user.getWeight(),user.getUserID()});
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
         
            return new Alarm(id,High,Low);
        }
        cursor.close();
        return null;

    }

    public void addalarm(Alarm alarm, Context context)
    {
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("insert into alarm  values(?,?,?)  ",
                new Object[]{ alarm.getUserID(),alarm.getHigh(),alarm.getLow()});
        db.close();
    }

    public void updatealarm(Alarm alarm,Context context)
    {

        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getReadableDatabase();
        db.execSQL("update alarm set High = ?,Low = ? where UserID = ?  ",
                new Object[]{alarm.getHigh(),alarm.getLow(), alarm.getUserID()});
        db.close();
    }
}
