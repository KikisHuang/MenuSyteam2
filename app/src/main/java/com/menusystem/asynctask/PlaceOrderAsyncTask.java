package com.menusystem.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.menusystem.bean.Append;
import com.menusystem.bean.PlaceOrder;
import com.menusystem.util.HttpURlConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.menusystem.activity.MainActivity.ChangeOrderStatus;
import static com.menusystem.activity.MainActivity.DataJudge;
import static com.menusystem.bean.Verify.FOOD_PLACEORDER_HTTP;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.ORDERSUCCEED;
import static com.menusystem.util.AlertDialogUtils.getOnlyDialog;

/**
 * Created by ${Kikis} on 2016-08-16.
 * 下单线程;
 */
public class PlaceOrderAsyncTask extends AsyncTask {

    private static final String TAG = "PlaceOrderAsyncTask";

    String result = "";
    Activity Ac;
    List<PlaceOrder> plist;

    public PlaceOrderAsyncTask(Activity Ac) {

        this.Ac = Ac;
    }

    @Override
    protected Object doInBackground(Object[] params) {


        plist = (List<PlaceOrder>) params[0];


        String uri =MHTTP + FOOD_PLACEORDER_HTTP;

        result = HttpURlConnection.getHttpClient(plist, uri);

        return result;
    }

    @Override
    protected void onPostExecute(Object o) {

//        String result = (String) o;
        try {
            if (!result.equals("")) {


                if (result.equals("false")) {

//                    Toast.makeText(Ac, "下单失败！请联系服务员手动点单....", Toast.LENGTH_SHORT).show();

                    getOnlyDialog(Ac,"下单失败！请联系服务员手动点单....");

                    Log.i(TAG, "下单失败！result == " + result);

                    return;
                }


                JSONArray array = new JSONArray(result);

                List<Append> adlist = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jo = array.getJSONObject(i);

                    Append ad = new Append();

                    ad.setAppend(jo.getInt("Append"));
                    ad.setCSID(jo.getString("CSID"));
                    ad.setFoodID(jo.getString("FoodID"));
                    ad.setFoodNumber(jo.getInt("FoodNumber"));
                    ad.setFoodStatus(jo.getInt("FoodStatus"));

                    adlist.add(ad);
                }

                if (adlist.size() > 0) {

                    Toast.makeText(Ac, "已经成功下单,请耐心等待服务员稍候为您上菜!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "成功下单！result == " + result);
                    DataJudge(Ac);
                    ChangeOrderStatus(adlist);
                    ORDERSUCCEED = true;
//                    StartThread();
                    return;

                }

            } else {

                Toast.makeText(Ac, "无法连接服务器...", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "1111无法连接服务器！result == " + result);

                return;
            }


        } catch (Exception e) {

            Toast.makeText(Ac, "无法连接服务器...", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "抛出异常！ Error == " + e);

            return;

        }

    }
}
