package com.integration.networktechdemo.socket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.DateUtil;

@SuppressLint("HandlerLeak")
public class SocketActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SocketActivity";
    private EditText et_socket;
    private static TextView tv_socket;
    private MessageTransmit mTransmit; // 声明一个消息传输对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        et_socket = findViewById(R.id.et_socket);
        tv_socket = findViewById(R.id.tv_socket);
        findViewById(R.id.btn_socket).setOnClickListener(this);
        //创建消息传输对象
        mTransmit = new MessageTransmit();
        new Thread(mTransmit).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.btn_socket){
            //获取一个消息队列
            Message message = Message.obtain();
            //获取编辑框输入的信息内容
            message.obj = et_socket.getText().toString();
            //通过消息转移线程，由UI线程向子线程后端发送消息
            mTransmit.mSendHandler.sendMessage(message);
        }
    }

    /**
     * 接收来自后端的消息
     */
    public static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: "+ msg.obj.toString());
            String desc = "";
            if (tv_socket != null){
                desc = String.format("%s 收到服务器的应答消息：%s", DateUtil.getNowTime(), msg.obj.toString());
                tv_socket.setText(desc);
            }
        }
    };
}
