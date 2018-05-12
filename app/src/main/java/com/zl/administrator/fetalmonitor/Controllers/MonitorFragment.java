package com.zl.administrator.fetalmonitor.Controllers;




import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;


import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.Info;
import com.zl.administrator.fetalmonitor.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2018/4/2/002.
 */

public class MonitorFragment extends Fragment  {

    private Context context;
    Button bt_search;
    Button bt_connect;
    Button bt_record;

    TextView inforTv;
    TextView FHR;
    TextView TOCO;
    TextView AFM;


    // 蓝牙适配器
    BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();;

    // 从搜索列表中返回的蓝牙设备
    BluetoothDevice mBtDevice;
    // 绑定的蓝牙服务
    BluetoothService mBluetoothService = null;
    //当前登录用户
    String username ;

    final DBHandle dbHandle = new DBHandle();

    public MonitorFragment(Context context) {
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monitor_fragment,container,false);

        SharedPreferences preferences = context.getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        username = preferences.getString("username", "");
        bt_search = view.findViewById(R.id.bt_search);
        bt_connect = view.findViewById(R.id.bt_connect);
        bt_record = view.findViewById(R.id.bt_record);

        inforTv  = view.findViewById(R.id.infor);


        FHR = view.findViewById(R.id.FHR);
        TOCO = view.findViewById(R.id.TOCO);
        AFM = view.findViewById(R.id.AFM);



        bt_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mAdapter != null) {
                    if (mAdapter.isEnabled()) {

                       // getActivity().startActivity(new Intent(getActivity(), BluetoothListActivity.class));

                        Intent list = new Intent(getActivity(),BluetoothListActivity.class);

                        startActivityForResult(list, 20);
                    }
                }else
                    Toast.makeText(context, "没有蓝牙适配器", Toast.LENGTH_SHORT).show();
            }

        });

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBtDevice != null) {
                    if(mBluetoothService == null) {
                        bindFhrService();
                        Toast toast = Toast.makeText(context, "蓝牙服务启动中...",  Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        if(mBluetoothService.getReadingStatus())
                            Toast.makeText(context, getResources().getString(R.string.device_connected), Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(context, getResources().getString(R.string.device_connectting), Toast.LENGTH_SHORT).show();
                            mBluetoothService.cancel();
                            mBluetoothService.start();
                        }

                    }
                }
                else
                    Toast.makeText(context, getResources().getString(R.string.no_device_select), Toast.LENGTH_SHORT).show();

            }
        });
        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info = new Info();
                info.setUserID(username);
                info.setFHR(Integer.valueOf(FHR.getText().toString()));
                info.setTOCO(Integer.valueOf(TOCO.getText().toString()));
                info.setAFM(Integer.valueOf(AFM.getText().toString()));
                Date date = new Date();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                info.setTime(dateformat.format(date));
                try{
                    dbHandle.addinfo(info,context);
                    Toast toast = Toast.makeText(context, "保存成功",  Toast.LENGTH_SHORT);
                    toast.show();

                } catch (SQLException ex) {
                    Toast toast = Toast.makeText(context, "保存失败",  Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        return view;
    }


    private final int MSG_ERR = 2;
    private final int MSG_SERVICE_INFOR = 10;
    private final int MSG_SERVICE_STATUS = 11;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch(msg.what) {

                case MSG_SERVICE_INFOR:
                    int infor[] = msg.getData().getIntArray("infor");
                    showServiceInfor(infor);
                    break;

                case MSG_ERR:
                    String str = msg.getData().getString("err");
                    dispString(str);
                    break;
            }

        }

    };

    /** Standard activity result: operation canceled. */
    public static final int RESULT_CANCELED    = 0;
    /** Standard activity result: operation succeeded. */
    public static final int RESULT_OK           = -1;
    /**
     * 被调用的 Activity 返回结果
     */
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            BluetoothDevice device;
            device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            mBtDevice = device;

            String result = new String("" +
                    device.getName() + "\n" +
                    device.getAddress() + "\n");

            dispString(result);

        } else if(resultCode == RESULT_CANCELED) {

            String result = new String("requestCode:" + requestCode + "\n" +
                    "resultCode:" + resultCode + "\n" +
                    data.getExtras().getString("list")  + "\n");

            dispString(result);

        }

    }

    // 服务绑定标志
    public boolean serveiceBindFlag = false;
    /**
     * 绑定服务
     */
    private void bindFhrService() {
        Intent bindIntent = new Intent(getActivity(), BluetoothService.class);
        getActivity().bindService(bindIntent , mSCon, getActivity().BIND_AUTO_CREATE);
        serveiceBindFlag = true;
    }
    /**
     * 解除服务绑定
     */
    private void unbindFhrDervice() {
        getActivity().unbindService(mSCon);
        mBluetoothService = null;
        serveiceBindFlag = false;
    }

    /**
     * 服务绑定连接类，在这里要设置服务的蓝牙设备，设置回调接口，
     * 并启动服务的相关线程
     */
    private ServiceConnection mSCon = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            mBluetoothService.setBluetoothDevice(mBtDevice);
            mBluetoothService.setCallback(mCallback);
            mBluetoothService.start();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }

    };


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }




    /**
     * 添加字符串到信息显示区
     * @param s 要添加的字符串
     */
    public void dispString(String s)
    {
        // 将字符串添加到 TextView 显示
      inforTv.setText(s);


    }

    /**
     * 清空信息显示区，并且清空缓存
     */
    public void clearInfo() {
        inforTv.setText("");
    }

    /**
     * 显示服务信息
     * @param infor
     */
    public void showServiceInfor(int infor[]) {
        FHR.setText(String.valueOf(infor[0]));
        TOCO.setText(String.valueOf(infor[1]));
        AFM.setText(String.valueOf(infor[2]));

    }


    /**
     * 服务的回到接口的实现
     */
    BluetoothService.Callback mCallback = new BluetoothService.Callback() {

        @Override
        public void dispData(int data[]) {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putIntArray("infor", data);
            msg.setData(bundle);
            msg.what = MSG_SERVICE_INFOR;
            handler.sendMessage(msg);
        }
        @Override
        public void dispServiceStatus(String sta) {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putString("status", sta);
            msg.setData(bundle);
            msg.what = MSG_SERVICE_STATUS;
            handler.sendMessage(msg);
        }

        @Override
        public  void dispInfor(String infor){

        }
    };


}
