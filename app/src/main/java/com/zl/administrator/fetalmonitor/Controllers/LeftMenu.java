package com.zl.administrator.fetalmonitor.Controllers;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zl.administrator.fetalmonitor.R;


/**
 * Created by Administrator on 2018/4/3/003.
 */

public class LeftMenu extends Fragment {
    private DrawerLayout drawer_layout;
    ImageView heads_image;
    Button bt_guide;
    Button bt_tips;
    Button bt_about;
    Button bt_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left, container, false);

        heads_image = view.findViewById(R.id.heads_image);
        bt_guide = view.findViewById(R.id.bt_guide);
        bt_tips = view.findViewById(R.id.bt_tips);
        bt_about = view.findViewById(R.id.bt_about);
        bt_setting = view.findViewById(R.id.bt_setting);


        heads_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                drawer_layout.closeDrawer(Gravity.START);
            }
        });

        bt_guide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        });
        bt_tips.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), TipsActivity.class));
            }

        });
        bt_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        });
        bt_setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
            }

        });

        return view;

    }

    //暴露给Activity，用于传入DrawerLayout，因为点击后想关掉DrawerLayout
    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.drawer_layout = drawer_layout;
    }

}
