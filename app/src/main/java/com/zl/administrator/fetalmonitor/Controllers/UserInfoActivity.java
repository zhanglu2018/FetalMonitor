package com.zl.administrator.fetalmonitor.Controllers;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    public TextView tv_updateinfo;

    public String username;
    public String userheight;
    public String userweight;
    public String userpassword;
    public Context context;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_main);

        context = UserInfoActivity.this;

        final DBHandle dbHandle = new DBHandle();
        user = dbHandle.finduser("super", context);

        tv_username = findViewById(R.id.bt_username);
        tv_userheight = findViewById(R.id.bt_height);
        tv_userweight = findViewById(R.id.bt_weight);
        tv_updateinfo = findViewById(R.id.bt_updateuserinfo);

        tv_username.setText(user.getName());
        tv_userheight.setText(String.valueOf(user.getHeight()));
        tv_userweight.setText(String.valueOf(user.getWeight()));

        tv_updateinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                user.setName(tv_username.getText().toString());
                user.setHeight(Integer.parseInt(tv_userheight.getText().toString()));
                user.setWeight(Integer.parseInt(tv_userweight.getText().toString()));
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



    }



}
