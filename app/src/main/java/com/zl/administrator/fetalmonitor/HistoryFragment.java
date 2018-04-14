package com.zl.administrator.fetalmonitor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class HistoryFragment extends Fragment {
    private String content;
    public HistoryFragment(String content) {
        this.content = content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment,container,false);
        TextView txt_content = view.findViewById(R.id.txt_content);

        return view;
    }

}
