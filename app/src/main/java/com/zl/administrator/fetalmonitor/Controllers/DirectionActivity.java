package com.zl.administrator.fetalmonitor.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/4/7/007.
 */

public class DirectionActivity extends AppCompatActivity {

    private ImageButton bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_main);

        bt_back = findViewById(R.id.bt_direction_back);
        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}