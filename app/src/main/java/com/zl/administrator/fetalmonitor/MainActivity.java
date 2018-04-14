package com.zl.administrator.fetalmonitor;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zl.administrator.fetalmonitor.Models.MyDBOpenHelper;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;

    //Fragment Object
    private MonitorFragment monitorFragment;
    private HistoryFragment historyFragment;
    private KnowledgeFragment knowledgeFragment;
    private FragmentManager fragmentManager;

    private DrawerLayout drawer_layout;
    private Button left_menu_button;
    private LeftMenu leftMenu;
    private FragmentManager leftfragmentManager;

    private Context context;

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

        leftfragmentManager = getFragmentManager();
        leftMenu = (LeftMenu) leftfragmentManager.findFragmentById(R.id.left_menu);

        context = MainActivity.this;
        initViews();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (checkedId){
            case R.id.rb_monitor:
                if(monitorFragment == null){
                    monitorFragment = new MonitorFragment("这是监测页面");
                    fragmentTransaction.add(R.id.main_view,monitorFragment);
                }else{
                    fragmentTransaction.show(monitorFragment);
                }
                break;
            case R.id.rb_history:
                if(historyFragment == null){
                    historyFragment = new HistoryFragment("这是历史记录页面");
                    fragmentTransaction.add(R.id.main_view,historyFragment);
                }else{
                    fragmentTransaction.show(historyFragment);
                }
                break;
            case R.id.rb_knowledge:
                if(knowledgeFragment == null){
                    knowledgeFragment = new KnowledgeFragment("这是孕期知识库页面");
                    fragmentTransaction.add(R.id.main_view,knowledgeFragment);
                }else{
                    fragmentTransaction.show(knowledgeFragment);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(monitorFragment != null)fragmentTransaction.hide(monitorFragment);
        if(historyFragment != null)fragmentTransaction.hide(historyFragment);
        if(knowledgeFragment != null)fragmentTransaction.hide(knowledgeFragment);
    }

    private void initViews() {
        drawer_layout =  findViewById(R.id.drawer_layout);
        left_menu_button = findViewById(R.id.left_menu_button);

        left_menu_button.setOnClickListener(this);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.START);

        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {
                drawer_layout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.START);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        leftMenu.setDrawerLayout(drawer_layout);
    }

    @Override
    public void onClick(View v) {
        drawer_layout.openDrawer(Gravity.LEFT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.START);    //解除锁定
    }

}
