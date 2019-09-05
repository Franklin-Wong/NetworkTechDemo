package com.integration.networktechdemo.thread;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Wongerfeng on 2019/8/19.
 */
public class ProgressAsyncTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "ProgressAsyncTask";
    private String mBookName;
    private OnProgressListener mOnProgressListener;

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

    /**
     *
     * @param bookName 操作对象
     */
    public ProgressAsyncTask(String bookName) {
        mBookName = bookName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnProgressListener.onBegin(mBookName);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i(TAG, "onProgressUpdate: values[0]="+ values[0] +"; length = "+values.length);
        mOnProgressListener.onProgressUpdate(mBookName, values[0], 100);

    }

    @Override
    protected String doInBackground(String... strings) {
        int i = 0;
        for (; i <= 100; i += 5) {

            try {
                //模拟网络加载进度
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        Log.i(TAG, "doInBackground: 数组="+strings[0] +"  ; length= "+ strings.length);
        //返回的是bookName
        return strings[0];
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mOnProgressListener.onFinish(mBookName);

    }

    /**
     * 设置监听器，监听任务执行的状态
     */
    public interface OnProgressListener{
        /**
         * 操作
         * @param result
         */
        void onCancel(String result);
        void onBegin(String result);
        void onProgressUpdate(String result, int progress, int subProgress);
        void onFinish(String result);
    }
}
