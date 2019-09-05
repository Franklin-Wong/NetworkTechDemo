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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
@SuppressLint("HandlerLeak")
public class NetAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NetAddressActivity";
    public static final String HOST_URL = "192.168.0.232";
    private EditText et_host_name;
    private TextView tv_host_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_address);
        et_host_name = findViewById(R.id.et_host_name);
        tv_host_name = findViewById(R.id.tv_host_name);
        findViewById(R.id.btn_host_name).setOnClickListener(this);
        et_host_name.setText(HOST_URL);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_host_name){
///            new CheckThread(HOST_URL).start();
            new CheckThread(et_host_name.getText().toString());
        }

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_host_name.setText("主机检查结果如下: "+msg.obj.toString());

        }
    };

    private class CheckThread extends Thread{

        String desc = "";
        String mHostName = "";
        public CheckThread(String hostName) {
            mHostName = hostName;
        }

        @Override
        public void run() {
            super.run();
            //获取一个消息
            Message message = Message.obtain();

            //获取主机名称
            try {
                InetAddress address = InetAddress.getByName(mHostName);
                //判断主机是否可连接
                boolean reachable = address.isReachable(5000);
                //拼接描述信息
                desc = reachable ? "可以连接":"不可连接";
                if (reachable){
                    desc = String.format("%s\n 主机名称：%s\n主机地址%s", desc,address.getHostName(), address.getHostAddress());
                }
                //添加参数到消息
                message.what = 0;
                message.obj = desc;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                message.obj = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                message.obj = e.getMessage();
            }
            Log.i(TAG, "run: desc = "+ desc);
            mHandler.sendMessage(message);
        }
    }
}
