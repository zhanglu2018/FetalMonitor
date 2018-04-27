package com.zl.administrator.fetalmonitor.Models;

import java.security.PrivateKey;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class User {
    private String UserID;
    private String Name;
    private String Password;
    private int    Height;
    private int    Weight;
    private Date   DueDate;

    public User() {
    }

    public User(String UserID, String Name, String Password,int Height,int Weight,Date DueDate) {
        this.UserID = UserID;
        this.Name = Name;
        this.Password = Password;
        this.Height = Height;
        this.Weight = Weight;
        this.DueDate = DueDate;

    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date Duedate) {
        this.DueDate = DueDate;
    }

}
