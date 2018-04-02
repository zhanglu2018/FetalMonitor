package com.zl.administrator.fetalmonitor;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;

    //Fragment Object
    private MonitorFragment monitorFragment;
    private HistoryFragment historyFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_channel = findViewById(R.id.rb_monitor);
        rb_channel.setChecked(true);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (checkedId){
            case R.id.rb_monitor:
                if(monitorFragment == null){
                    monitorFragment = new MonitorFragment();
                    fragmentTransaction.add(R.id.main_view,monitorFragment);
                }else{
                    fragmentTransaction.show(monitorFragment);
                }
                break;
            case R.id.rb_history:
                if(historyFragment == null){
                    historyFragment = new HistoryFragment();
                    fragmentTransaction.add(R.id.main_view,historyFragment);
                }else{
                    fragmentTransaction.show(historyFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(monitorFragment != null)fragmentTransaction.hide(monitorFragment);
        if(historyFragment != null)fragmentTransaction.hide(historyFragment);
    }
}
