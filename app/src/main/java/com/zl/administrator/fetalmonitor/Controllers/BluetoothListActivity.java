package com.zl.administrator.fetalmonitor.Controllers;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.administrator.fetalmonitor.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListActivity extends Activity {
	
	/** 当前界面listView 的引用 */
	private ListView listView;
	/** 显示搜索状态 */
	private TextView searchStateTv;
	/** 停止搜索按钮 */
	private Button stopSearch;
	
	/** 用来存储搜索到的蓝牙设备 */
	List<BluetoothDevice> deviceBt;
	/** ListView 数据缓存，用来存储搜索到的蓝牙设备 */
	ArrayList<String> bluetoothsList;
	/** ListView 的适配器，用来关联  bluetoothsList*/
	ArrayAdapter<String> listAdapter;
	/** 获取蓝牙适配器 */
	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	
	/** 搜索中提示文字 */
	String[] searching;
	/** 搜索提示文字 */
	String searched;
	
	/** 选择的设备提示文字 */
	String deviceSelect;
	
	/** 状态的提示文字 */
	String status;
	
	/** 提示信息 */
	String back;
	
	/** 该值表示搜索中，值为1 */
	private final int BT_SEARCHING = 1;
	/** 该值表示搜索完成或搜索停止，值为0 */
	private final int BT_SEARCHED = 0;
	
	/** 该值指示搜索蓝牙状态<p>=true，正在搜索<p>=false，搜索完成或搜索停止  */
	private boolean searchingFlag = true;
	
	
	Handler handler = new Handler() {
		int count=0;
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case BT_SEARCHED:
				searchStateTv.setText(searched);
				break;
				
			case BT_SEARCHING:
				searchStateTv.setText(searching[count]);
				count++;
				if(count >= 3)
					count = 0;
				break;

			}
			super.handleMessage(msg);
		}
		
	};
	
	/**
	 * 搜索蓝牙监听器，主要是监听 BluetoothDevice.ACTION_FOUND
	 */
	private BroadcastReceiver _foundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String mdevice;
			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if(deviceBt.contains(device))
				return;
			
			String mdeviceName = device.getName();
			String mdeviceAddress = device.getAddress();
			mdevice = mdeviceAddress + "\n" + mdeviceName;
			addToList(mdevice);
			deviceBt.add(device);
		}
		
	};

	/**
	 * 完成搜索监听器，监听 BluetoothAdapter.ACTION_DISCOVERY_FINISHED
	 */
	private BroadcastReceiver _finishedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			searchingFlag = false;
			unregisterBroadcastReceiver();
		}
		
	};
	
	/**
	 * 启动搜索线程
	 */
	Thread _searchStart = new Thread() {

		@Override
		public void run() {
			mBluetoothAdapter.startDiscovery();
		}
		
	};
	
	Thread _showSearchState = new Thread() {

		@Override
		public void run() {
			while(searchingFlag) {
				
				// 发送消息，在 TextView 中显示搜索中
				handler.sendEmptyMessage(BT_SEARCHING);
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			
			// 如果完成搜索，发送消息跟新显示状态
			handler.sendEmptyMessage(BT_SEARCHED);
			
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        
		searching = getResources().getStringArray(R.array.searching);
		searched = getResources().getString(R.string.finished_search);
		deviceSelect = getResources().getString(R.string.device_select);
		status = getResources().getString(R.string.status);
		back = getResources().getString(R.string.back);
		
		setContentView(R.layout.list_tool);
		
		searchingFlag = true;
		
		deviceBt = new ArrayList<BluetoothDevice>();
		
        bluetoothsList = new ArrayList<String>();
        
        searchStateTv = findViewById(R.id.seatch_state);
		stopSearch = findViewById(R.id.stop_search);
		listView = findViewById(R.id.search_list);
		
		initListView();
		initButton();
		regesiterBroadcastReceiver();

		if(mBluetoothAdapter.isEnabled()) {
			_searchStart.start();
			_showSearchState.start();
		}
	}
	
	/**
	 * 添加设备名称到 ListView 的缓存 bluetoothsList 
	 * @param mdevice 要添加的设备名
	 */
	protected void addToList(String mdevice) {
		bluetoothsList.add(mdevice);
		// 更新 listView 列表
		listAdapter.notifyDataSetChanged();

	}
	
	/**
	 * 清空 ListView 的缓存 bluetoothsList
	 */
	protected void cleanList() {
		bluetoothsList.clear();
		// 更新 listView 列表
		listView.setAdapter(listAdapter);
	}

	/**
	 * 注册广播接收器
	 */
	public void regesiterBroadcastReceiver() {
		IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(_foundReceiver, foundFilter); 
		IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(_finishedReceiver, finishedFilter); 
	}
	
	/**
	 * 注销广播接收器
	 */
	public void unregisterBroadcastReceiver() {
		unregisterReceiver(_foundReceiver);
		unregisterReceiver(_finishedReceiver);
	}
	
	/**
	 * 初始化 ListView ，设置 ListView 事件处理方法
	 */
	private void initListView() {
		// 创建适配器
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				bluetoothsList);
		
		// 设置为 listView 的适配器
		listView.setAdapter(listAdapter);
		
		// 设置 listView 点击事件的处理
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

				// 判断是否正在搜索蓝牙，如果是，则取消搜索
				if(mBluetoothAdapter.isDiscovering() == true)
					mBluetoothAdapter.cancelDiscovery();

				Toast.makeText(BluetoothListActivity.this,
						deviceSelect + bluetoothsList.get(position),
						Toast.LENGTH_SHORT).show();
				// 返回结果给调用本 Activity 的 原Activity
				Intent result = new Intent();
				result.putExtra(BluetoothDevice.EXTRA_DEVICE, deviceBt.get(position));
				setResult(RESULT_OK, result);
				finish();
			}
		});
	}
	
	
	/**
	 * 初始化 stopSearch ，设置按钮的事件处理方法
	 */
	private void initButton() {
		stopSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 判断是否正在搜索蓝牙，如果是，则取消搜索
				if(mBluetoothAdapter.isDiscovering() == true)
					mBluetoothAdapter.cancelDiscovery();
				
				Toast.makeText(BluetoothListActivity.this, status + mBluetoothAdapter.isDiscovering(), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		switch (keyCode) {
		
		case KeyEvent.KEYCODE_BACK :

			// 判断是否正在搜索蓝牙，如果是，则取消搜索
			if(mBluetoothAdapter.isDiscovering() == true)
				mBluetoothAdapter.cancelDiscovery();

			Intent result = new Intent();
			result.putExtra("list", back);
			setResult(RESULT_CANCELED, result);
			finish();
			break;
		
		case KeyEvent.KEYCODE_HOME:
			
			break;
			
		}

		return super.onKeyDown(keyCode, event);

	}
	
	

}
