package com.llh.rxvolly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private TextView et_name;
    private TextView et_number;
    private Button btn_search;
    private ListView mListView;
    private List<Courier> courierList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    //初始化View
    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        btn_search = findViewById(R.id.btn_search);
        mListView = findViewById(R.id.mListView);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                /**
                 * 1.获取输入框的内容
                 * 2.判断输入框的内容是否为空
                 * 3.获取json
                 * 4.解析json
                 */
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                //拼接url
                String url = "http://v.juhe.cn/exp/index?key=bf82ce388b07c90ac0c19567e65947de&com="+name+"&no="+number;
                //2.判断输入框的内容是否为空
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){
                    //请求
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //super.onSuccess(t);
                            //Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();
                            Log.d("llhData",t);
                            //解析json
                            parsingJson(t);
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            //super.onFailure(error);
                            Toast.makeText(MainActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            //把字符串文件转换为json对象
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            //遍历数组
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                //创建一个实体类对象//传递json值
                Courier courier = new Courier();
                courier.setDatetime(json.getString("datetime"));
                courier.setRemark(json.getString("remark"));
                courier.setZone(json.getString("zone"));
                courierList.add(courier);
            }
            //倒序
            Collections.reverse(courierList);
            //创建适配器
            CourierAdapter adapter = new CourierAdapter(this,courierList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
