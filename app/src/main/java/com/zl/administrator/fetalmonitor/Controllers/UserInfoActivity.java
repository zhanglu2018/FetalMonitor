package com.zl.administrator.fetalmonitor.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.User;
import com.zl.administrator.fetalmonitor.R;


/**
 * Created by Administrator on 2018/4/15/015.
 */

public class UserInfoActivity extends AppCompatActivity  {

    public EditText tv_username;
    public EditText tv_userheight;
    public EditText tv_userweight;
    public EditText tv_userpassword;
    public EditText tv_duedate;
    public TextView tv_updateinfo;
    public ImageButton bt_back;
    public Context context;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_main);

        context = UserInfoActivity.this;

        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        final DBHandle dbHandle = new DBHandle();
        user = dbHandle.finduser(username, context);

        bt_back = findViewById(R.id.bt_userinfo_back);
        tv_username = findViewById(R.id.bt_username);
        tv_userheight = findViewById(R.id.bt_height);
        tv_userweight = findViewById(R.id.bt_weight);
        tv_duedate = findViewById(R.id.bt_duedate);
        tv_userpassword = findViewById(R.id.tv_password);
        tv_updateinfo = findViewById(R.id.bt_updateuserinfo);

        if (user.getName() != null) {
            tv_username.setText(user.getName());
        }else {
            tv_username.setText("");
        }
        if(String.valueOf(user.getHeight()) != null){
            tv_userheight.setText(String.valueOf(user.getHeight()));
        } else {
            tv_userheight.setText("");
        }
        if(String.valueOf(user.getWeight())!= null) {
            tv_userweight.setText(String.valueOf(user.getWeight()));
        } else {
            tv_userweight.setText("");
        }
        if (user.getDueDate() != null){
            tv_duedate.setText(user.getDueDate());
        }else {
            tv_duedate.setText("");
        }


        tv_updateinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                user.setName(tv_username.getText().toString());
                user.setHeight(Integer.parseInt(tv_userheight.getText().toString()));
                user.setWeight(Integer.parseInt(tv_userweight.getText().toString()));
                user.setDueDate(tv_duedate.getText().toString());
                if(tv_userpassword.getText().toString() != null){
                    user.setPassword(tv_userpassword.getText().toString());
                }
                try {
                    dbHandle.updateuser(user, context);
                    Toast toast = Toast.makeText(context, "保存成功",  Toast.LENGTH_SHORT);
                    toast.show();

                } catch (SQLException ex) {
                    Toast toast = Toast.makeText(context, "保存失败",  Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
