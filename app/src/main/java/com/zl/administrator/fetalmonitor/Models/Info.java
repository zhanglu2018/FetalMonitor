package com.zl.administrator.fetalmonitor.Models;

import java.util.Date;

/**
 * Created by Administrator on 2018/5/1/001.
 */

public class Info {
    private String UserID;
    private int    FHR;
    private int    TOCO;
    private int    AFM;
    private String   Time;
    public Info() {
    }

    public Info(String UserID,int FHR,int TOCO, int AFM, String Time) {
        this.UserID = UserID;
        this.FHR = FHR;
        this.TOCO = TOCO;
        this.AFM = AFM;
        this.Time = Time;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getFHR() {
        return FHR;
    }

    public void setFHR(int FHR) {
        this.FHR = FHR;
    }

    public int getTOCO() {
        return TOCO;
    }

    public void setTOCO(int TOCO) {
        this.TOCO = TOCO;
    }

    public int getAFM() {
        return AFM;
    }

    public void setAFM(int AFM) {
        this.AFM = AFM;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
