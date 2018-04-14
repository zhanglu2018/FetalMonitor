package com.zl.administrator.fetalmonitor.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.concurrent.ExecutionException;


/**
 * Created by Administrator on 2018/4/14/014.
 */

public class DBHandle {

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;


    public int finduser(String id, String pwd ,Context context)
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

        //ContentValues values = new ContentValues();
       // values.put("UserID", id);
       // values.put("Password",pwd);
       //values.put("Name", "");
       // values.put("Height",1);
       // values.put("Weight", 1);
       // values.put("DueDate", id);
        //values.put("DueDate",duedate);
        //参数依次是：表名，强行插入null值得数据列的列名，一行记录的数据
        //db.insert("user",null, values);
        db.execSQL("INSERT INTO user(UserID,Password) values(?,?)",
                 new String[]{id,pwd});
        db.close();
    }
}
