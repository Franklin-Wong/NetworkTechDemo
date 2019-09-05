package com.integration.networktechdemo.updown;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.integration.networktechdemo.R;
import com.integration.networktechdemo.utils.DateUtil;
import com.integration.networktechdemo.widget.TextProgressCircle;

import java.util.HashMap;

public class DownloadImageActivity extends AppCompatActivity {
    private static final String TAG = "DownloadImageActivity";
    private Spinner sp_image_url;
    private ImageView iv_image_url;
    private TextProgressCircle tpc_progress; // 定义一个文本进度圈对象
    private TextView tv_image_result;
    //下载管理器
    private DownloadManager mDownloadManager;
    //下载任务编码
    private long mDownloadId = 0;
    //是否首次进入页面
    private boolean isFirstSelelct = true;
    //本地保存图片路径
    private String mPath;
    //下载状态映射
    private static HashMap<Integer, String> sHashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        iv_image_url = findViewById(R.id.iv_image_url);
        // 从布局文件中获取名叫tpc_progress的文本进度圈
        tpc_progress = findViewById(R.id.tpc_progress);
        tv_image_result = findViewById(R.id.tv_image_result);
        sp_image_url = findViewById(R.id.sp_image_url);
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //添加映射元素
        sHashMap.put(DownloadManager.STATUS_PENDING, "挂起");
        sHashMap.put(DownloadManager.STATUS_RUNNING,"运行");
        sHashMap.put(DownloadManager.STATUS_PAUSED,"暂停");
        sHashMap.put(DownloadManager.STATUS_SUCCESSFUL,"成功");
        sHashMap.put(DownloadManager.STATUS_FAILED, "失败");

        initSpinner();

    }

    private void initSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_select, imageDescArray);
        sp_image_url.setEnabled(true);
        sp_image_url.setAdapter(arrayAdapter);
        sp_image_url.setPrompt("请选择要下载的图片");
        sp_image_url.setOnItemSelectedListener(new MyItemSelectListener());

    }

    private String[] imageDescArray = {
            "洱海公园", "丹凤亭", "宛在堂", "满庭芳", "玉带桥",
            "眺望洱海", "洱海女儿", "海心亭", "洱海岸边", "烟波浩渺"
    };
    private String[] imageUrlArray = {
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/nYJcslMIrGeDrujE5KZF2xBW8rjXMIVetZfrOAlSamM!/b/dPwxB5iaEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/Adcl9XVS.RBED4D8shjceYHOhhR*6mcNyCcq24kJG2k!/b/dPwxB5iYEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/bg*X6nT03YUReoJ97ked266WlWG3IzLjBdwHpKqkhYY!/b/dOg5CpjZEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/JOPAKl9BO1wragCEIVzXLlHwj83qVhb8uNuHdmVRwP4!/b/dPwxB5iSEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/7hHOgBEOBshH*7YAUx7RP0JzPuxRBD727mblw9TObhc!/b/dG4WB5i2EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/m4Rjx20D9iFL0D5emuYqMMDji*HGQ2w2BWqv0zK*tRk!/b/dGp**5dYEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/swfCMVl7Oefv8xgboV3OqkrahEs33KO7XwwH6hh7bnY!/b/dECE*5e9EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/tpRlB0oozaD9PyBtCmf3pQ5QY0keJJxYGX93I7n5NwQ!/b/dAyVmZiVEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/wMX2*LM6y.mBsFIYu8spAa7xXWUkPD.GHyazd.vMmYA!/b/dGYwoZjREQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "https://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/2vl1n0KmKTPCv944MVJgLxKAhMiM*sqajIFQ43c*9DM!/b/dPaoCJhuEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
    };


    private class MyItemSelectListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //启动下载任务
            if (isFirstSelelct){
                //
                isFirstSelelct = false;
                return;
            }
            //修改选择器状态
            sp_image_url.setEnabled(false);
            tv_image_result.setText("正在下载任务");
            iv_image_url.setImageDrawable(null);
            tpc_progress.setVisibility(View.VISIBLE);
            tpc_progress.setProgress(0, 100);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrlArray[position]));
            request.setTitle("正在下载"+ imageDescArray[position]);
            request.setDescription(DateUtil.getNowTime() +"正在下载任务"+ mDownloadId);
            request.setVisibleInDownloadsUi(false);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setDestinationInExternalFilesDir(DownloadImageActivity.this, Environment.DIRECTORY_DCIM, position +".jpg");

            //在子线程中启动任务，并获取任务ID
            mDownloadId = mDownloadManager.enqueue(request);
            //延迟100毫秒，启动下载任务
            mHandler.postDelayed(mRunnable, 100);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isFinishing = false;
            //创建查询器
            DownloadManager.Query query = new DownloadManager.Query();
            //设置查询的对象
            query.setFilterById(mDownloadId);
            //想下载器发起查询
            Cursor cursor = mDownloadManager.query(query);
            //开启查询
            while (cursor.moveToNext()){
                //查询获取下载文件各种信息的索引
                int nameId = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                int uri = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                int type = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);
                int size = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int sizeByNow = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                int downloadStatus = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                //通过索引，获取对应的VALUE信息
                int progress = (int)(100 * cursor.getLong(sizeByNow) / cursor.getLong(size)) ;
                //设置当前下载的进度
                tpc_progress.setProgress(progress, 100);
                if (progress == 100){
                    isFinishing = true;
                }

                int status = isFinishing ? DownloadManager.STATUS_SUCCESSFUL : cursor.getInt(downloadStatus);

                if (cursor.getString(uri) == null){
                    break;
                }
                //解析URI，获取图片保存的路径
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
                    String string = cursor.getString(uri);
                    mPath = Uri.parse(string).getPath();
                }else {
                    mPath = cursor.getString(uri);
                }

                String desc = "";
                //通过索引获取VALUE，将VALUE添加到描述信息中
                desc = String.format("%s图片地址：%s\n", desc, cursor.getString(nameId));
                desc = String.format("%s图片类型：%s\n", desc, cursor.getString(type));
                desc = String.format("%s图片大小：%s\n", desc, cursor.getLong(size));
                desc = String.format("%s图片已下载大小：%s\n", desc, cursor.getLong(sizeByNow));
                desc = String.format("%s图片下载进度：%s%%\n", desc, progress);
                desc = String.format("%s图片状态：%s\n", desc, sHashMap.get(status));

                tv_image_result.setText(desc);
                Log.i(TAG, "run: desc = "+ desc);
            }

            //下载完毕，关闭游标
            cursor.close();
            //
            if (!isFinishing){
                mHandler.postDelayed(mRunnable, 100);
            }else {
                //下载完毕，更新选项按钮状态
                sp_image_url.setEnabled(true);
                tpc_progress.setVisibility(View.GONE);
                iv_image_url.setImageURI(Uri.parse(mPath));
            }

        }
    };
}
