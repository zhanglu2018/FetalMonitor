package com.zl.administrator.fetalmonitor.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/5/1/001.
 */

public class AboutActivity extends AppCompatActivity {

    private ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
        bt_back = findViewById(R.id.bt_about_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
