package com.zl.administrator.fetalmonitor.Models;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class Alarm {

        private String UserID;
        private int    High;
        private int    Low;
        public Alarm() {
        }

        public Alarm(String UserID,int High,int Low) {
            this.UserID = UserID;
            this.High = High;
            this.Low = Low;

        }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getHigh() {
        return High;
    }

    public void setHigh(int High) {
        this.High = High;
    }

    public int getLow() {
        return Low;
    }

    public void setLow(int Low) {
        this.Low = Low;
    }


}
