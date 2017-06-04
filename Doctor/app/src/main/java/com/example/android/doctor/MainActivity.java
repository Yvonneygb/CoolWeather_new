/*
package com.example.android.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private Button wen;
    private Button yuyue;
    private Button me;
    private ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       wen =(Button) findViewById(R.id.wen);
       yuyue =(Button) findViewById(R.id.yuyue);
        me = (Button)  findViewById(R.id.me);



        //初始化参数
        final Animation in = AnimationUtils.loadAnimation(this,R.anim.in_alpha);
        final Animation out = AnimationUtils.loadAnimation(this,R.anim.out_alpha);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        //开始图片自动播放，第一个为总时间，第二个为间隔时间
        //意思是每隔1秒就会回调一次方法onTick,5秒之后就回调onFinish方法
        new CountDownTimer(5000,1000){
           @Override
            public void onFinish(){
               viewFlipper.setInAnimation(in);
               viewFlipper.setInAnimation(out);
               viewFlipper.showNext();
               start();
           }
            @Override
            public  void onTick(long millisUtilFinished){

            }

        }.start();












        //Button点击事件——主界面-问医生
        wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this,ZhuActivity.class);
                startActivity(i);
            }
        });

                Intent in = new Intent (MainActivity.this,YuyueActivity.class);
                startActivity(in);
                //Button点击事件——主界面-预约挂号
                yuyue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
            }
        });

        //Button点击事件——主界面-个人中心
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent (MainActivity.this,MeActivity.class);
                startActivity(in);
            }
        });












        //列表
        listView =(ListView) findViewById(R.id.list_news);
        final List<String> data = new ArrayList<>() ;
        for(int i=0;i<4;i++){
            data.add("List" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);


        //每个Item 的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(listView.get(arg1).equals("0")){
                    Intent i = new Intent(getRequest3());
                    startActivity(i);
                }
            }
        });



    }
}




*/











/**
 *健康资讯调用示例代码 － 聚合数据
 *在线接口文档：http://www.juhe.cn/docs/145
 **/

/*
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class JuheDemo {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";


    //配置您申请的KEY
    public static final String APPKEY ="05845a9751df826cebd79a0e7a8590a8";

    //1.资讯分类列表
    public static void getRequest1(){
        String result =null;
        String url ="http://op.juhe.cn/yi18/news/newsclass";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY
        params.put("dtype","json");//返回数据的格式,xml或json，默认json

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);

            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2.取得资讯信息列表
    public static void getRequest2(){
        String result =null;
        String url ="http://op.juhe.cn/yi18/news/list";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json
        params.put("limit","1");//每页个数
        params.put("page","1");//第几页

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.取得详细资讯信息
    public static void getRequest3(){
        String result =null;
        String url ="http://op.juhe.cn/yi18/news/show";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("id","");//资讯的id
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */

/*
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
*/