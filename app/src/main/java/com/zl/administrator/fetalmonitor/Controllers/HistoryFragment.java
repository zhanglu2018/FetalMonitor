package com.zl.administrator.fetalmonitor.Controllers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.Info;
import com.zl.administrator.fetalmonitor.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2/002.
 */

public class HistoryFragment extends Fragment {
    private Context context;
    private ListView history_list;
    private HistoryAdapter mAdapter = null;
    private LinkedList<Info> mData = null;
    private List<Info> info;
    public HistoryFragment(Context context) {
        this.context= context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment,container,false);
        TextView txt_empty = view.findViewById(R.id.txt_content);
        history_list = view.findViewById(R.id.history_list);

        final SharedPreferences preferences = getActivity().getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        final String username = preferences.getString("username", "");

        final DBHandle dbHandle = new DBHandle();

        txt_empty.setText("还没有历史记录！");
        history_list.setEmptyView(txt_empty);
        mData = new LinkedList<Info>();
        mAdapter = new HistoryAdapter(mData,username,context);

        info = dbHandle.findinfo(username,context);
        if (info != null){
            for(int i =0;i<info.size();i++) {

                mAdapter.add(info.get(i));
                
            }
        }
        history_list.setAdapter(mAdapter);
        return view;
    }

}
