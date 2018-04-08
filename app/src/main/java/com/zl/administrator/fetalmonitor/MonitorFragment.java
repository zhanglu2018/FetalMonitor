package com.zl.administrator.fetalmonitor;




import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Administrator on 2018/4/2/002.
 */

public class MonitorFragment extends Fragment {

    private String content;
    Button bt_search;
    public MonitorFragment(String content) {
        this.content = content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monitor_fragment,container,false);
        bt_search = view.findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), BluetoothListActivity.class));
            }

        });
        return view;
    }
    


}
