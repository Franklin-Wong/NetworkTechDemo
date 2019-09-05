package com.integration.networktechdemo.updown;

import android.os.AsyncTask;

/**
 * Created by Wongerfeng on 2019/8/29.
 */
public class UploadTask extends AsyncTask<String,Void, String> {
    private static final String TAG = "UploadTask";
    private OnUploadTaskListener mUploadTaskListener;

    public UploadTask() {
        super();
    }

    public void setUploadTaskListener(OnUploadTaskListener uploadTaskListener) {
        mUploadTaskListener = uploadTaskListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        //获取参数中的元素
        String loadUrl = strings[0];
        String filePath = strings[1];
        String result = HttpUploadUtil.upload(loadUrl, filePath);

        return result;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mUploadTaskListener.onUploadTaskListener(s);

    }

    public interface OnUploadTaskListener {
        void onUploadTaskListener(String s);
    }


}
