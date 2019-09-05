package com.integration.networktechdemo.mails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.bean.MailBox;
import com.integration.networktechdemo.bean.MailItem;

import java.util.ArrayList;

public class FoldListActivity extends AppCompatActivity {
    private static final String TAG = "FoldListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_list);

        initMailBox();
    }

    private void initMailBox() {
        ExpandableListView elv_list = findViewById(R.id.elv_list);
        //创建邮箱的列表
        ArrayList<MailBox> box_list = new ArrayList<MailBox>();
        //给邮箱列表中添加but类型的邮箱
        box_list.add(new MailBox(R.drawable.mail_folder_inbox, "收件箱", getRecvMail()));
        box_list.add(new MailBox(R.drawable.mail_folder_outbox, "发件箱", getSentMail()));
        box_list.add(new MailBox(R.drawable.mail_folder_draft, "草稿箱", getDraftMail()));
        box_list.add(new MailBox(R.drawable.mail_folder_recycle, "废件箱", getRecycleMail()));
        //设置邮箱列表的适配器和监听器
        MailExpandableAdapter adapter = new MailExpandableAdapter(this, box_list);
        elv_list.setAdapter(adapter);
        //设置父布局 的监听器
        elv_list.setOnGroupClickListener(adapter);
        //设置子布局的 监听器
        elv_list.setOnChildClickListener(adapter);
    }

    private ArrayList<MailItem> getRecycleMail() {
        ArrayList<MailItem> mail_list = new ArrayList<>();
        mail_list.add(new MailItem("怎么被删除了啊1", "2018年5月11日"));
        mail_list.add(new MailItem("怎么被删除了啊2", "2018年5月13日"));
        mail_list.add(new MailItem("怎么被删除了啊3", "2018年5月15日"));
        mail_list.add(new MailItem("怎么被删除了啊4", "2018年5月10日"));
        mail_list.add(new MailItem("怎么被删除了啊5", "2018年5月14日"));
        return mail_list;
    }

    private ArrayList<MailItem> getDraftMail() {

        ArrayList<MailItem> mail_list = new ArrayList<>();
        mail_list.add(new MailItem("暂时放在草稿箱吧1", "2018年5月14日"));
        mail_list.add(new MailItem("暂时放在草稿箱吧2", "2018年5月11日"));
        mail_list.add(new MailItem("暂时放在草稿箱吧3", "2018年5月15日"));
        mail_list.add(new MailItem("暂时放在草稿箱吧4", "2018年5月10日"));
        mail_list.add(new MailItem("暂时放在草稿箱吧5", "2018年5月13日"));
        return mail_list;
    }

    private ArrayList<MailItem> getSentMail() {

        ArrayList<MailItem> mail_list = new ArrayList<>();
        mail_list.add(new MailItem("邮件发出去了吗1","2018年5月15日"));
        mail_list.add(new MailItem("邮件发出去了吗2", "2018年5月14日"));
        mail_list.add(new MailItem("邮件发出去了吗3", "2018年5月11日"));
        mail_list.add(new MailItem("邮件发出去了吗4", "2018年5月13日"));
        mail_list.add(new MailItem("邮件发出去了吗5", "2018年5月10日"));

        return mail_list;
    }

    private ArrayList<MailItem> getRecvMail() {

        ArrayList<MailItem> mail_list = new ArrayList<>();
        mail_list.add(new MailItem("这里是收件箱呀1", "2018年5月15日"));
        mail_list.add(new MailItem("这里是收件箱呀2", "2018年5月10日"));
        mail_list.add(new MailItem("这里是收件箱呀3", "2018年5月14日"));
        mail_list.add(new MailItem("这里是收件箱呀4", "2018年5月11日"));
        mail_list.add(new MailItem("这里是收件箱呀5", "2018年5月13日"));

        return mail_list;
    }


}
