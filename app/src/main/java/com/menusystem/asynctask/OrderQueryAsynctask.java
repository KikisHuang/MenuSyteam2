package com.menusystem.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.menusystem.bean.Order;
import com.menusystem.util.AlertDialogUtils;
import com.menusystem.util.HttpURlConnection;
import com.menusystem.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.menusystem.activity.MainActivity.AddOrder;
import static com.menusystem.activity.MainActivity.GainAllPrices;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.bean.Verify.ORDERQUERY;
import static com.menusystem.bean.Verify.VERIFY_KEY;
import static com.menusystem.util.ProgressDialogUtil.getProgressDialog;

/**
 * Created by ${Kikis} on 2016-09-19.
 */
public class OrderQueryAsynctask extends AsyncTask{
    private static final String TAG = "OrderQueryAsynctask" ;
    private Activity context;
    private ProgressDialog pd ;
    private String result = "";
    public OrderQueryAsynctask(Activity context) {

        this.context= context;

    }

    @Override
    protected void onPreExecute() {
        pd = getProgressDialog(context,pd,"正在查询上次未完成订单,请稍后...");
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String csid = String.valueOf(params[0]);

        String menukey = VERIFY_KEY;

        String URL = MHTTP+ORDERQUERY;

        StringBuilder sb = new StringBuilder(URL);

        sb.append("?");

        try {

            sb.append("CSID=" + URLEncoder.encode(csid.toString(), "UTF-8") + "&");

            sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

            result = HttpURlConnection.getHttpURlConnection(sb);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Object o) {

        try {

        if(result.length()>0&&!result.equals("false")&&!result.isEmpty()){

            JSONArray array = new JSONArray(result);

            for(int i=0;i<array.length();i++){

                JSONObject object = array.getJSONObject(i);

                Order od = new Order();
                od.setFoodName(object.getString("FoodName"));
                od.setNumber(object.getInt("Number"));
                od.setSellPrice(object.getString("SellPrice"));
                od.setAppend(object.getInt("Append"));
                od.setState(object.getInt("FoodStatus"));
                AddOrder(od,context);

            }
            GainAllPrices();
            pd.cancel();
        }


        if(result.equals("false")){

            AlertDialogUtils.getOnlyDialog(context,"没有查询到订单。");

            SharedPreference.removeSharedPreference(context, "CSID");

            Log.i(TAG, MY_LOGCAT_TAG + "清空SharedPreference信息...");

            pd.cancel();

            return;
        }else{



        }

        }catch (Exception e){

            Log.i(TAG,"与服务器连接异常...Error = "+e);
            pd.cancel();
            return;
        }
    }
}

