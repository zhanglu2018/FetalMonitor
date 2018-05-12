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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.Models.Alarm;
import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/4/22/022.
 */

public class SettingActivity extends AppCompatActivity {

    public TextView bt_updatesetting;
    public EditText high;
    public EditText low;
    public Switch aSwitch;
    public Button bt_pretermit;

    public ImageButton bt_back;
    public Context context;

    public Alarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
// 获取当前登录用户
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        context = SettingActivity.this;
        high = findViewById(R.id.high);
        low = findViewById(R.id.low);
        aSwitch = findViewById(R.id.alarm_swith);
        bt_pretermit = findViewById(R.id.pretermit);
        bt_updatesetting = findViewById(R.id.bt_updatesetting);
        bt_back = findViewById(R.id.bt_setting_back);

        alarm = new Alarm();

        final DBHandle dbHandle = new DBHandle();
        alarm = dbHandle.findalarm(username, context);
        if(alarm == null){
            alarm = new Alarm();
            alarm.setUserID(username);
            alarm.setHigh(160);
            alarm.setLow(120);
            alarm.setSwitch(1);
            dbHandle.addalarm(alarm,context);
        }


        high.setText(String.valueOf(alarm.getHigh()));
        low.setText(String.valueOf(alarm.getLow()));
        if (alarm.getSwitch() == 1){
            aSwitch.setChecked(true) ;
        }

        bt_pretermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high.setText(String.valueOf(160));
                low.setText(String.valueOf(120));
                aSwitch.setChecked(true);
            }
        });
        bt_updatesetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alarm.setHigh(Integer.parseInt(high.getText().toString()));
                alarm.setLow(Integer.parseInt(low.getText().toString()));

                if (aSwitch.isChecked()){
                    alarm.setSwitch(1);
                } else {
                    alarm.setSwitch(0);
                }

                try {
                    dbHandle.updatealarm(alarm, context);
                    Toast toast = Toast.makeText(context, "保存成功",  Toast.LENGTH_SHORT);
                    toast.show();

                } catch (Exception ex) {
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