package com.zl.administrator.fetalmonitor.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/4/14/014.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, "fetalmonitor.db", null, 1); }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (\n" +
                "    UserID   TEXT PRIMARY KEY\n" +
                "                  NOT NULL,\n" +
                "    Name     TEXT,\n" +
                "    Password TEXT,\n" +
                "    Height   INT,\n" +
                "    Weight   INT,\n" +
                "    DueDate  DATE\n" +
                ");\n");
        db.execSQL("CREATE TABLE info (\n" +
                "UserID TEXT NOT NULL,\n" +
                "FHR INT,\n" +
                "TOCO INT,\n" +
                "AFM INT,\n" +
                "Time DATE NOT NULL,\n" +
                "FOREIGN KEY (\n" +
                "UserID\n" +
                ")\n" +
                "REFERENCES user (UserID),\n" +
                "PRIMARY KEY (\n" +
                "UserID,\n" +
                "Time\n" +
                ")\n" +
                ");\n");
        db.execSQL("CREATE TABLE alarm (\n" +
                "    UserID TEXT PRIMARY KEY\n" +
                "                NOT NULL,\n" +
                "    High   INT,\n" +
                "    Low    INT,\n" +
                "    FOREIGN KEY (\n" +
                "        UserID\n" +
                "    )\n" +
                "    REFERENCES user (UserID) \n" +
                ");\n");
        db.execSQL("INSERT INTO user(UserID,Password) values(\"super\",\"123456\")");
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}