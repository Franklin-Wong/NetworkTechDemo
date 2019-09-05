package com.integration.networktechdemo.mails;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.bean.MailBox;
import com.integration.networktechdemo.bean.MailItem;

import java.util.ArrayList;

/**
 * Created by Wongerfeng on 2019/9/4.
 */
public class MailExpandableAdapter implements ExpandableListAdapter, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
    private static final String TAG = "MailExpandableAdapter";
    private Context mContext; // 声明一个上下文对象
    private ArrayList<MailBox> mBoxList; // 邮箱队列

    public MailExpandableAdapter(Context context, ArrayList<MailBox> boxList) {
        mContext = context;
        mBoxList = boxList;
    }

    public class ViewHolderBox{
        public ImageView iv_box;
        public TextView tv_box;
        public TextView tv_count;
    }

    public class ViewHolderMail{
        public CheckBox ck_mail;
        public TextView tv_date;
    }
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        MailBox mailBox = mBoxList.get(groupPosition);
        String title = mailBox.box_title;
        String desc = String.format("您点击的是%s", title);
        Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
        //返回 true ，将不能打开邮件列表
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //设置邮件的点击后状态
        ViewHolderMail viewHolderMail = (ViewHolderMail) v.getTag();
        viewHolderMail.ck_mail.setChecked(!(viewHolderMail.ck_mail.isChecked()));
        //返回FALSE，不再向下传递
        return true;
    }


    @Override
    public int getGroupCount() {
        return mBoxList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mBoxList.get(groupPosition).mail_list.size();
    }

    @Override
    public MailBox getGroup(int groupPosition) {

        return mBoxList.get(groupPosition);
    }

    @Override
    public MailItem getChild(int groupPosition, int childPosition) {
        return mBoxList.get(groupPosition).mail_list.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderBox viewHolderBox;
        if (convertView == null) {
            viewHolderBox = new ViewHolderBox();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_box, null);
            viewHolderBox.iv_box = convertView.findViewById(R.id.iv_box);
            viewHolderBox.tv_box = convertView.findViewById(R.id.tv_box);
            viewHolderBox.tv_count = convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolderBox);
        } else {
            viewHolderBox = (ViewHolderBox) convertView.getTag();
        }
        MailBox mailBox = (MailBox) mBoxList.get(groupPosition);
        if (mailBox != null) {
            viewHolderBox.tv_count.setText(mailBox.mail_list.size() + "封邮件");
            viewHolderBox.tv_box.setText(mailBox.box_title);
            viewHolderBox.iv_box.setImageResource(mailBox.box_icon);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolderMail viewHolderMail;
        if (convertView == null) {
            viewHolderMail = new ViewHolderMail();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mail, null);
            viewHolderMail.ck_mail = convertView.findViewById(R.id.ck_mail);
            viewHolderMail.tv_date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolderMail);
        } else {
            viewHolderMail = (ViewHolderMail) convertView.getTag();
        }
        final MailItem mailItem = (MailItem) mBoxList.get(groupPosition).mail_list.get(childPosition);
        if (mailItem != null) {
            viewHolderMail.tv_date.setText(mailItem.mail_date);
            viewHolderMail.ck_mail.setText(mailItem.mail_title);
            viewHolderMail.ck_mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    viewHolderMail.ck_mail.setChecked(!isChecked);
                    //点击提示邮件信息
                    MailBox mailBox = mBoxList.get(groupPosition);
                    String desc = String.format("您点击的邮件是：%s， 标题是 ：%s", mailBox.box_title, mailItem.mail_title);
                    Toast.makeText(mContext, desc, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onCheckedChanged: groupPosition = "+ groupPosition+"___childPosition = "+ childPosition);
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

}
