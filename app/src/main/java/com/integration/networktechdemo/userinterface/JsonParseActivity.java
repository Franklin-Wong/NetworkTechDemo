package com.integration.networktechdemo.userinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.integration.networktechdemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonParseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JsonParseActivity";
    private TextView tv_json;
    private String mJsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);
        tv_json = findViewById(R.id.tv_json);
        findViewById(R.id.btn_construct_json).setOnClickListener(this);
        findViewById(R.id.btn_parser_json).setOnClickListener(this);
        findViewById(R.id.btn_traverse_json).setOnClickListener(this);

        mJsonStr = getJsonStr();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_construct_json){
            tv_json.setText(mJsonStr);
        }else if (v.getId() == R.id.btn_parser_json){
            tv_json.setText(parseJson(mJsonStr));

        }else if (v.getId() == R.id.btn_traverse_json){
            tv_json.setText(traverseJson(mJsonStr));
        }
    }

    /**
     * 通过迭代器遍历json串
     * @param jsonStr
     * @return
     */
    private String traverseJson(String jsonStr) {

        String result = "";
        try {
            //创建json对象
            JSONObject jsonObject = new JSONObject(jsonStr);
            //获取jsonObject的key,创建外部迭代器
            Iterator<String> iterator = jsonObject.keys();

            while (iterator.hasNext()){
                String key = iterator.next();
                String value = jsonObject.getString(key);
                result = String.format("%skey=%s, value=%s\n", result, key,value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 构造一个json
     * @return
     */
    private String getJsonStr() {

        String str = "";
        //创建json对象
        try {
            JSONObject jsonObject = new JSONObject();
            //添加一个 “name” 的参数
            jsonObject.put("name", "address");

            //创建一个json数组
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < 3; i++) {
                JSONObject item = new JSONObject();
                item.put("item", "集合的第"+i+"项元素");
                jsonArray.put(item);
            }

            jsonObject.put("list", jsonArray);
            jsonObject.put("count", jsonArray.length());
            jsonObject.put("desc", "这是测试字符串");

            str = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 解析字符串
     * @param jsonStr
     * @return
     */
    private String parseJson(String jsonStr) {
        //创建字符串 结果对象
        String result = "";
        //创建json对象
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            String name = jsonObject.getString("name");
            String desc = (String) jsonObject.getString("desc");
            int count = (int) jsonObject.getInt("count");

            //解析json中的字段
            result = String.format("%sname=%s\n", result, name);
            result  = String.format("%scount=%s\n", result, count);
            result = String.format("%sdesc=%s\n", result, desc);

            //获取list中的元素
            JSONArray list = (JSONArray) jsonObject.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject object = (JSONObject) list.get(i);
                String item = object.getString("item");
                result = String.format("%s\titem=%s\n", result, item);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
