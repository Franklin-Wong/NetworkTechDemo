package com.integration.networktechdemo.appstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.integration.networktechdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wongerfeng on 2019/9/2.
 */
public class ApkAdapter extends BaseAdapter {
    private Context mContext;
    private List<ApkInfo> mInfoList = new ArrayList<>();

    public ApkAdapter(Context context, List<ApkInfo> infoList) {
        mContext = context;
        mInfoList = infoList;
    }

    @Override
    public int getCount() {

        return mInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apk, null);
            viewHolder.tv_file_name = convertView.findViewById(R.id.tv_file_name);
            viewHolder.tv_package_name = convertView.findViewById(R.id.tv_package_name);
            viewHolder.tv_version_name = convertView.findViewById(R.id.tv_version_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ApkInfo apkInfo = mInfoList.get(position);
        if (apkInfo != null) {
            viewHolder.tv_version_name.setText(apkInfo.version_name);
            viewHolder.tv_package_name.setText(apkInfo.package_name);
            viewHolder.tv_file_name.setText(apkInfo.file_name);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView tv_file_name;
        public TextView tv_package_name;
        public TextView tv_version_name;

    }
}
