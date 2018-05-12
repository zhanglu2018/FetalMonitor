package com.zl.administrator.fetalmonitor.Controllers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.MyDBOpenHelper;
import com.zl.administrator.fetalmonitor.Models.User;
import com.zl.administrator.fetalmonitor.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/4/14/014.
 */

public class RegActivity extends AppCompatActivity {

    public Button bt_register;
    public EditText et_regid;
    public EditText et_regpassword;
    public EditText et_regduedate;

    private SQLiteDatabase db;
    private MyDBOpenHelper myDBHelper;
    private Context context;
    public DBHandle dbHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_main);


        context = RegActivity.this;

        myDBHelper = new MyDBOpenHelper(context, "fetalmonitor.db", null, 1);
        db = myDBHelper.getWritableDatabase();
        dbHandle = new DBHandle();

        bt_register = findViewById(R.id.registerBtn);
         et_regduedate = findViewById(R.id.duedate);
        final Calendar c = Calendar.getInstance();     //创建日期选择对象
        et_regduedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //显示弹窗 哈哈哈^_^~~~~
                DatePickerDialog dialog = new DatePickerDialog(RegActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        et_regduedate.setText(DateFormat.format("yyy-MM-dd", c).toString());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                et_regid = findViewById(R.id.reguserId);
                et_regpassword = findViewById(R.id.regpassword);

                User user = new User();
                user.setUserID(et_regid.getText().toString());
                user.setPassword(et_regpassword.getText().toString());
                user.setDueDate(et_regduedate.getText().toString());

                dbHandle.adduser(user,context);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);


            }
        });
    }
}
