package com.integration.networktechdemo.thread;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.integration.networktechdemo.R;

public class AsyncTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ProgressAsyncTask.OnProgressListener {

    private static final String TAG = "AsyncTaskActivity";
    private TextView tv_async;
    private ProgressBar pb_async; // 声明一个进度条对象
    private ProgressDialog mDialog; // 声明一个进度对话框对象
    private int mStyle = 0;

    private int BAR_PROGRESS = 0;
    private int DIALOG_HORIZONTAL = 1;
    private int DIALOG_CIRCLE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        tv_async = findViewById(R.id.tv_async);
        // 从布局文件中获取名叫pb_async的进度条
        pb_async = findViewById(R.id.pb_async);
        initBookSpinner(); // 初始化书籍选择下拉框

    }

    private int[] mStyleArray = new int[]{BAR_PROGRESS, DIALOG_CIRCLE, DIALOG_HORIZONTAL};

    private String[] bookArray = {"三国演义", "西游记", "红楼梦"};
    private void initBookSpinner() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_select, bookArray);

        Spinner spinner = findViewById(R.id.sp_style);
        spinner.setSelection(0);
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("请选择下载的PDF文件");
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        startTask(mStyleArray[position], bookArray[position]);
        Log.i(TAG, "onItemSelected: position = "+position);

    }

    private void startTask(int style, String bookName) {
        mStyle = style;
        //创建下载异步任务
        ProgressAsyncTask asyncTask = new ProgressAsyncTask(bookName);
        asyncTask.setOnProgressListener(this);
        asyncTask.execute(bookName);
        /*ProgressAsyncTask asyncTask1 = new ProgressAsyncTask(bookName+"1");
        asyncTask1.setOnProgressListener(this);
        asyncTask1.execute(bookName+"1");
        ProgressAsyncTask asyncTask2 = new ProgressAsyncTask(bookName+"2");
        asyncTask2.setOnProgressListener(this);
        asyncTask2.execute(bookName+"2");
        ProgressAsyncTask asyncTask3 = new ProgressAsyncTask(bookName+"3");
        asyncTask3.setOnProgressListener(this);
        asyncTask3.execute(bookName+"3");
        ProgressAsyncTask asyncTask4 = new ProgressAsyncTask(bookName+"4");
        asyncTask4.setOnProgressListener(this);
        asyncTask4.execute(bookName+"4");
        ProgressAsyncTask asyncTask5 = new ProgressAsyncTask(bookName+"5");
        asyncTask5.setOnProgressListener(this);
        asyncTask5.execute(bookName);
        ProgressAsyncTask asyncTask6 = new ProgressAsyncTask(bookName+"6");
        asyncTask6.setOnProgressListener(this);
        asyncTask6.execute(bookName+"1");
        ProgressAsyncTask asyncTask7 = new ProgressAsyncTask(bookName+"7");
        asyncTask7.setOnProgressListener(this);
        asyncTask7.execute(bookName+"2");
        ProgressAsyncTask asyncTask8 = new ProgressAsyncTask(bookName+"8");
        asyncTask8.setOnProgressListener(this);
        asyncTask8.execute(bookName+"3");
        ProgressAsyncTask asyncTask9 = new ProgressAsyncTask(bookName+"9");
        asyncTask9.setOnProgressListener(this);
        asyncTask9.execute(bookName+"9");*/
    }

    private void closeDialog(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "onNothingSelected: ");
    }

    @Override
    public void onCancel(String result) {
        tv_async.setText(String.format("您加载的文件《%S》已经取消加载", result));

        closeDialog();
    }

    @Override
    public void onBegin(String result) {

        //根据进度框类型，显示不同的进度框
        if (mDialog == null || !mDialog.isShowing()){
            if (mStyle == DIALOG_CIRCLE){
                mDialog = ProgressDialog.show(this, "正在加载" , "文件" + result);
            }else if (mStyle == DIALOG_HORIZONTAL){

                mDialog = new ProgressDialog(this);
                mDialog.setTitle("正在加载");
                mDialog.setMessage("正在加载文件"+ result);
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.show();
            }
        }

    }

    @Override
    public void onProgressUpdate(String result, int progress, int subProgress) {
        //根据九分裤类型，显示更新进度
        if (mStyle == BAR_PROGRESS){
            pb_async.setProgress(progress);
            pb_async.setSecondaryProgress(subProgress);
        }else if (mStyle == DIALOG_HORIZONTAL){
            mDialog.setProgress(progress);
            mDialog.setSecondaryProgress(subProgress);
        }

        tv_async.setText(String.format("现在文件《%s》已经加载了%s", result, progress+"%"));

    }

    @Override
    public void onFinish(String result) {
        tv_async.setText(String.format("您加载的文件《%S》已加载完毕", result));
        closeDialog();
    }
}
