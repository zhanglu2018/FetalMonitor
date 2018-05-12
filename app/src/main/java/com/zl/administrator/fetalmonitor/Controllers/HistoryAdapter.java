package com.zl.administrator.fetalmonitor.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zl.administrator.fetalmonitor.Models.Alarm;
import com.zl.administrator.fetalmonitor.Models.DBHandle;
import com.zl.administrator.fetalmonitor.Models.Info;
import com.zl.administrator.fetalmonitor.R;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/5/1/001.
 */

public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<Info> mData;
    private String username;

    public HistoryAdapter() {}

    public HistoryAdapter(LinkedList<Info> mData, String username, Context mContext) {
        this.mData = mData;
        this.username = username;
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);
            holder = new ViewHolder();
            holder.history_Time = convertView.findViewById(R.id.history_Time);
            holder.history_FHR = convertView.findViewById(R.id.history_FHR);
            holder.history_TOCO = convertView.findViewById(R.id.history_TOCO);
            holder.history_AFM = convertView.findViewById(R.id.history_AFM);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Alarm alarm = new Alarm();
        alarm.setUserID(username);

        final DBHandle dbHandle = new DBHandle();
        alarm = dbHandle.findalarm(username, mContext);
        if(alarm == null){
            alarm = new Alarm();
            alarm.setSwitch(0);
            alarm.setHigh(160);
            alarm.setLow(120);
        }
        if(alarm.getSwitch() == 1){
            if (mData.get(position).getFHR() > alarm.getHigh() || mData.get(position).getFHR() < alarm.getLow()) {
                holder.history_FHR.setTextColor(Color.parseColor("#FF0000"));
            }
        }

        holder.history_Time.setText(String.valueOf(mData.get(position).getTime()));
        holder.history_FHR.setText(String.valueOf(mData.get(position).getFHR()));
        holder.history_TOCO.setText(String.valueOf(mData.get(position).getTOCO()));
        holder.history_AFM.setText(String.valueOf(mData.get(position).getAFM()));
        return convertView;
    }

    private class ViewHolder{
        TextView history_Time;
        TextView history_FHR;
        TextView history_TOCO;
        TextView history_AFM;

    }
    public void add(Info info) {
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(info);
        notifyDataSetChanged();
    }

}