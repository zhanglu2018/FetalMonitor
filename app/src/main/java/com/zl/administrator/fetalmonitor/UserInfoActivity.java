package com.zl.administrator.fetalmonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class UserInfoActivity extends AppCompatActivity  {

    public TextView tv_username;
    public TextView tv_userheight;
    public TextView tv_userweight;
    public TextView tv_userpassword;
    public TextView tv_updateinfo;

    public String username;
    public String userheight;
    public String userweight;
    public String userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_main);

    }
}
