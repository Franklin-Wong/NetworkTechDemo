package com.integration.networktechdemo.thread;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.DateUtil;

@SuppressLint("HandlerLeak")
public class ProgressDialogActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProgressDialogActivity";
    private ProgressDialog mProgressBar;
    private TextView tv_result;
    private String mStyleDesc;

    private int TYPE_CLOSE = 1;
    private int TYPE_UPDATE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
        tv_result = findViewById(R.id.tv_result);

        initStyleSpinner();

    }

    private void initStyleSpinner() {
        //创建适配器
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<String>(this, R.layout.item_select, descArray);
        //初始化spinner
        Spinner spinner = findViewById(R.id.sp_style);
        //设置spinner属性
        spinner.setPrompt("请选择对话框样式");
        spinner.setSelection(0);
        spinner.setAdapter(arrayAdapter);
        //设置选项按钮的回调, 处理响应事件
        spinner.setOnItemSelectedListener(this);
    }
    private String[] descArray = {"圆圈进度", "水平进度条"};
    private int[] styleArray = {ProgressDialog.STYLE_SPINNER,
            ProgressDialog.STYLE_HORIZONTAL};


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //当进度框未弹出
        if (mProgressBar == null ||!mProgressBar.isShowing()){
            int style = styleArray[position];
            mStyleDesc = descArray[position];
            //圆形进度框
            if (style == ProgressDialog.STYLE_SPINNER){

                mProgressBar = new ProgressDialog(ProgressDialogActivity.this);
                mProgressBar.setTitle("请稍候");
                mProgressBar.setMessage( "正在努力加载页面");
                mProgressBar.show();
                //handler控制对话框关闭
                mHandler.postDelayed(mCloseDialogTask, 2000);

            }else if (style == ProgressDialog.STYLE_HORIZONTAL){
                //横向进度框
                mProgressBar = new ProgressDialog(ProgressDialogActivity.this);
                mProgressBar.setTitle("请您稍后");
                mProgressBar.setMessage("正在努力加载......");
                mProgressBar.setMax(100);
                mProgressBar.setProgressStyle(style);
                mProgressBar.show();
                new RefreshThread().start();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == TYPE_UPDATE){
                mProgressBar.setProgress(msg.arg1);
            }else if (what == TYPE_CLOSE){
                post(mCloseDialogTask);
            }


        }
    };
    /**
     * 关闭对话框
     */
    private Runnable mCloseDialogTask = new Runnable() {
        @Override
        public void run() {
            if(mProgressBar.isShowing()){
                mProgressBar.dismiss();
                //更新文案提示
                tv_result.setText("加载完成时间"+ DateUtil.getNowTime() +" "+ mStyleDesc);
//                mHandler.sendEmptyMessage(TYPE_CLOSE);
            }
        }
    };

    public class RefreshThread extends Thread{

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10; i++) {
                //创建一个显示进度的消息队列
                Message message = Message.obtain();
                //设置参数类型
                message.what = TYPE_UPDATE;
                //设置参数赋值
                message.arg1 = i * 10;
                //发送消息
                mHandler.sendMessage(message);
                //每发送10%完毕后，等待半秒
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //发送进度完毕的空信息
            mHandler.sendEmptyMessage(TYPE_CLOSE);
        }
    }
}
