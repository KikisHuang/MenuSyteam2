package com.menusystem.util;

import android.util.Log;

import com.menusystem.bean.PlaceOrder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class HttpURlConnection {
    private static final String TAG = "HttpURlConnection";
    static String t = null;

    /**
     * HttpURLConnection方法
     * 公用
     */
    public static String getHttpURlConnection(StringBuilder sb) throws IOException {


        URL url = null;
        url = new URL(sb.toString());

        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式
        conn.setRequestMethod("GET");

        conn.setReadTimeout(10000);// 请求超时时间
        conn.setConnectTimeout(10000);// 设置连接超时时间
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
//        Log.i("网络状态码打印", " = " + conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            Log.i("网络状态码", " =  200");


            // 获取对象流
            InputStream in = conn.getInputStream();
            // 创建字节输出对象流
            byte buffer[] = new byte[1024];

            int len = 0;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            in.close();
            baos.close();


            t = new String(baos.toByteArray());


        } else {

            Log.i("网络状态码打印", " = " + conn.getResponseCode());

            return null;
        }

        if (t != null) {

            return t;

        } else {

            return null;

        }


    }

    /**
     * HttpClient方法
     * 独立用于下单传输菜品json
     */

    public static String getHttpClient(List<PlaceOrder> plist, String uri) {

        JSONArray array;

        String result = "";

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(String.valueOf(uri));

        //添加http头信息
        httppost.addHeader("Authorization", "your token"); //认证token
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("User-Agent", "imgfornote");

        array = new JSONArray();
        try {
            for (int i = 0; i < plist.size(); i++) {

                JSONObject obj = new JSONObject();

                obj.put("SystemID", plist.get(i).getSystemID());
                obj.put("CSID", plist.get(i).getCSID());
                obj.put("DeskID", plist.get(i).getDeskID());
                obj.put("OrderStatus", plist.get(i).getOrderStatus());
                obj.put("OrderCoupon", plist.get(i).getOrderCoupon());
                obj.put("StartDate", plist.get(i).getStartDate());
                obj.put("StartTime", plist.get(i).getStartTime());
                obj.put("EndDate", plist.get(i).getEndDate());
                obj.put("EndTime", plist.get(i).getEndTime());
                obj.put("SellPrice", plist.get(i).getSellPrice());
                obj.put("FoodStatus", plist.get(i).getFoodStatus());
                obj.put("FoodId", plist.get(i).getFoodId());
                obj.put("FoodNumber", plist.get(i).getFoodNumber());

                Log.i(TAG, "AsyncTask信息打印 ++++" + obj);

                array.put(obj);
            }


//            httppost.setEntity(new StringEntity(array.toString(),"UTF-8"));
            httppost.setEntity(new StringEntity(URLEncoder.encode(array.toString(),"UTF-8")));
            HttpResponse response;
            response = httpclient.execute(httppost);

            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();

            Log.i(TAG, "下单网络状态码打印" + code);

            if (code == 200) {

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                result = reader.readLine();

                Log.i(TAG, "成功接收数据!!!" + result);

                return result;

            } else {


                return "";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}

