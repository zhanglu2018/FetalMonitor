package com.zl.administrator.fetalmonitor.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/5/1/001.
 */

public class GuideActivity extends AppCompatActivity {

    private ImageButton bt_back;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_main);
        bt_back = findViewById(R.id.bt_guide_back);
        webView = findViewById(R.id.webview_guide);
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        webView.loadUrl("file:///android_asset/guide_web.html");
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
