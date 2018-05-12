package com.zl.administrator.fetalmonitor.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.MyDBOpenHelper;
import com.zl.administrator.fetalmonitor.R;

/**
 * Created by Administrator on 2018/3/20/020.
 */

public class LoginActivity extends AppCompatActivity {

    public Button bt_login;
    public Button bt_reg;
    public EditText userid;
    public EditText password;
    public String id ;
    public String pwd ;

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private Context context;
    public DBHandle dbHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);



        bt_login = findViewById(R.id.loginBtn);
        bt_reg = findViewById(R.id.regBtn);
        context = LoginActivity.this;
        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getWritableDatabase();

        dbHandle = new DBHandle();

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegActivity.class);
                startActivity(intent);

            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userid = findViewById(R.id.userId);
                password = findViewById(R.id.password);
                id = userid.getText().toString();
                pwd = password.getText().toString();

                int result = dbHandle.login(id, pwd, context);
                    //存在数据才返回true
                    if(result == 1)
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user name", userid.getText().toString());///把用户名字绑定并带入后续activity

                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("username",id);
                        editor.commit();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
}
