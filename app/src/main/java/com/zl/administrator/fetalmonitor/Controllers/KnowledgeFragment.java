package com.zl.administrator.fetalmonitor.Controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class KnowledgeFragment extends Fragment {
    private String content;
    private WebView webView;
    public KnowledgeFragment(String content) {
        this.content = content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledge_fragment,container,false);
        webView = view.findViewById(R.id.knowledge_view);
        webView.loadUrl("http://www.babytree.com/");
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        return view;
    }
}
