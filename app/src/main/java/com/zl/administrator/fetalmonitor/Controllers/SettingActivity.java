package com.zl.administrator.fetalmonitor.Controllers;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public Context context;

    public Alarm alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        context = SettingActivity.this;
        high = findViewById(R.id.high);
        low = findViewById(R.id.low);
        aSwitch = findViewById(R.id.alarm_swith);
        bt_pretermit = findViewById(R.id.pretermit);
        bt_updatesetting = findViewById(R.id.bt_updatesetting);

        alarm = new Alarm();
        alarm.setUserID("super");

        final DBHandle dbHandle = new DBHandle();
        alarm = dbHandle.findalarm("super", context);
        if(alarm ==null){
            alarm.setHigh(160);
            alarm.setLow(120);
            dbHandle.addalarm(alarm,context);
        }


        high.setText(String.valueOf(alarm.getHigh()));
        low.setText(String.valueOf(alarm.getLow()));

        bt_updatesetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alarm.setHigh(Integer.parseInt(high.getText().toString()));
                alarm.setLow(Integer.parseInt(low.getText().toString()));

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

    }
}