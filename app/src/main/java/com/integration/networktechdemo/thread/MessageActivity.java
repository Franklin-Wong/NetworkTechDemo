package com.integration.networktechdemo.thread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.integration.networktechdemo.utils.DateUtil;
import com.integration.networktechdemo.R;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MessageActivity";
    /** 声明一个文本视图对象*/
    private TextView tv_message;

    private boolean isPlaying = false;
    private int BEGIN = 0;
    private int SCROLL = 1;
    private  int END = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();


    }

    private void initView() {
        // 从布局文件中获取名叫tv_control的文本视图
        tv_message = findViewById(R.id.tv_message);
        //文本的滚动方向为从左向右，从下向上
        tv_message.setGravity(Gravity.LEFT| Gravity.BOTTOM);
        tv_message.setMaxLines(8);
        tv_message.setMaxHeight(8);
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.btn_start_message).setOnClickListener(this);
        findViewById(R.id.btn_stop_message).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start_message){
            //当消息不在播放状态，启动播放线程
            if (!isPlaying){
                isPlaying = true;
                new PlayThread().start();
            }
        }else if (v.getId() == R.id.btn_stop_message){
            isPlaying = false;
        }
    }
    private String[] mNewsArray = { "北斗三号卫星发射成功，定位精度媲美GPS",
            "美国赌城拉斯维加斯发生重大枪击事件", "日本在越南承建的跨海大桥未建完已下沉",
            "南水北调功在当代，数亿人喝上长江水", "马克龙呼吁重建可与中国匹敌的强大欧洲"
    };

    private class PlayThread extends Thread {

        @Override
        public void run() {
            super.run();

            //发送消息 开始
            mHandler.sendEmptyMessage(BEGIN);

            //当播放状态未改变
            while (isPlaying){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //创建一个滚动播放消息的message
                Message message = Message.obtain();
                //设置信息内容
                message.obj = mNewsArray[(int) (Math.random() * 30 % 5)];
                //设置信息类型
                message.what = SCROLL;
                //发送到服务器
                mHandler.sendMessage(message);
            }

            //保持 播放 状态
            isPlaying = true;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //发送播放结束的信息
            mHandler.sendEmptyMessage(END);
            //状态改为停止播放
            isPlaying = false;
            
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化文本内容，每次添加内容，保留原来的文本内容
            String desc = tv_message.getText().toString();
            int what = msg.what;
            if (what == BEGIN){

                desc = String.format("%s \n %S  %S", desc, DateUtil.getNowTime(), "开始播放新闻");
            }else if (what == END){
                desc = String.format("%s \n %S  %S", desc, DateUtil.getNowTime(), "播放新闻结束了");
            }else if (what == SCROLL){
                desc = String.format("%s \n %s %s", desc, DateUtil.getNowTime(), msg.obj);
            }
            tv_message.setText(desc);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
