package com.zl.administrator.fetalmonitor.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/4/7/007.
 */

public class TipsActivity extends AppCompatActivity {

    private Button bt_directions ;
    private Button bt_understand;

    private ImageButton bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips);

        bt_back = findViewById(R.id.bt_tips_back);
        bt_directions = findViewById(R.id.bt_directions);
        bt_understand = findViewById(R.id.bt_understand);
        bt_directions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TipsActivity.this,DirectionActivity.class));
            }

        });
        bt_understand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(TipsActivity.this,UnderstandActivity.class));
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
