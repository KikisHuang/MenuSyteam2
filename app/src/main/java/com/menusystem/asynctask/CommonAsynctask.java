package com.menusystem.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.menusystem.bean.Order;
import com.menusystem.interfaces.AsyncResponse;
import com.menusystem.util.AlertDialogUtils;
import com.menusystem.util.HttpURlConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.menusystem.activity.MainActivity.AddOrder;
import static com.menusystem.activity.MainActivity.GainAllPrices;
import static com.menusystem.activity.MainActivity.SetCsid;
import static com.menusystem.bean.Verify.VERIFY_KEY;
import static com.menusystem.util.ProgressDialogUtil.getProgressDialog;

/**
 * Created by ${Kikis} on 2016-08-31.
 */

public class CommonAsynctask extends AsyncTask {

    private static final String TAG = "CommonAsynctask";
    private AsyncResponse asyncResponse;
    private Activity context;
    private List<String> list;
    String result = "";
    ProgressDialog pd;

    public void setOnAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public CommonAsynctask(Activity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        pd = getProgressDialog(context,pd,"正在校验桌号,请稍后...");
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String menukey = VERIFY_KEY;

        String value = String.valueOf(params[0]);
        String URL = String.valueOf(params[1]);

        StringBuilder sb = new StringBuilder(URL);

        sb.append("?");

        try {
            sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

            sb.append("value=" + URLEncoder.encode(value.toString(), "UTF-8") + "&");

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
            String result = (String) o;

            Log.i(TAG, "onPostExecute 返回值打印 --- " + result);
            if (result.equals("false")) {

//              Toast.makeText(context, "输出的桌号跟后台数据校验不匹配,请确认后再输入桌号,谢谢", Toast.LENGTH_SHORT).show();

                AlertDialogUtils.getOnlyDialog(context,"输出的桌号跟后台数据校验不匹配,请确认后再输入桌号,谢谢");

                Log.i(TAG, "输出的桌号跟后台数据校验不匹配");

                asyncResponse.onReceivedFailed();

                pd.cancel();
                return;
            }

            if (!result.isEmpty() && result.equals("true")) {

                asyncResponse.onReceivedSuccess();

                Log.i(TAG, "桌号校验成功..");
                pd.cancel();

                return;

            }

            if(!result.equals("false")&&!result.equals("true")&&!result.isEmpty()&&result.length()>0){


                JSONArray array = new JSONArray(result);

                JSONObject ob = array.getJSONObject(0);

                SetCsid(ob.getString("CSID"),context);

                for(int i=0;i<array.length();i++){

                    JSONObject object = array.getJSONObject(i);

                    Order od = new Order();
                    od.setFoodName(object.getString("FoodName"));
                    od.setNumber(object.getInt("Number"));
                    od.setSellPrice(object.getString("SellPrice"));
                    od.setAppend(object.getInt("Append"));
                    od.setState(object.getInt("FoodStatus"));
                    od.setDetailName(object.getString("DetailName"));

                    AddOrder(od,context);
                    Log.i(TAG, "获取到上次桌号未完成订单....result ====="+result);
                }
                asyncResponse.onReceivedSuccess();
                GainAllPrices();
                pd.cancel();
                return;
            }

        } catch (Exception e) {

            Log.i(TAG, "Error -----  抛出异常信息 = " + e);
            asyncResponse.onReceivedFailed();

            pd.cancel();

            return;
        }


    }
}
