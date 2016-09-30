package com.menusystem.asynctask;

import android.util.Log;

import com.menusystem.bean.OrderStatus;
import com.menusystem.util.HttpURlConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.menusystem.activity.MainActivity.ChangeOrderStatusTwo;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.ORDERSUCCEED;
import static com.menusystem.bean.Verify.ORDER_FOOD_STATUS_HTTP;
import static com.menusystem.bean.Verify.THREADCONTROL;
import static com.menusystem.bean.Verify.VERIFY_KEY;

/**
 * Created by ${Kikis} on 2016-08-18.
 */

public class PatrolThread extends Thread {

    public static final String TAG = "PatrolThread";

    String SystemId = "";
    String CSID = "";
    List<OrderStatus> slist;

    public PatrolThread(String CSID, String SystemId) {
        this.SystemId = SystemId;
        this.CSID = CSID;
    }

    @Override
    public void run() {

        while (ORDERSUCCEED) {

            try {
                THREADCONTROL = true;
                Log.i(TAG, "启动了定时轮训菜品状态线程.....123");
                Thread.sleep(30000);

                String menukey = VERIFY_KEY;

                StringBuilder sb = new StringBuilder(MHTTP + ORDER_FOOD_STATUS_HTTP);

                sb.append("?");

                sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");
                sb.append("CSID=" + URLEncoder.encode(CSID.toString(), "UTF-8") + "&");
                sb.append("SystemId=" + URLEncoder.encode(SystemId.toString(), "UTF-8") + "&");

                Log.i(TAG, "PatrolThread ---- CSID == " + CSID + "PatrolThread ---- SystemId" + SystemId);

                String Result = HttpURlConnection.getHttpURlConnection(sb);


                if (!Result.equals("false")) {

                    JSONArray array = new JSONArray(Result);

                    slist = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {

                        OrderStatus OS = new OrderStatus();
                        JSONObject object = array.getJSONObject(i);


                        OS.setFoodID(object.getString("FoodID"));
                        OS.setFoodNumber(object.getInt("FoodNumber"));
                        OS.setFoodStatus(object.getInt("FoodStatus"));
                        OS.setAppend(object.getInt("Append"));


                        slist.add(OS);

                    }

                    if (slist.size() > 0) {

                        ChangeOrderStatusTwo(slist);
                        Log.i(TAG, "进入修改状态码方法");

                    }

                }

                if (Result.equals("false")) {

                    Log.i(TAG, "菜品状态没有更改....打印的result ==" + Result);
                }


            } catch (Exception e) {

                Log.i(TAG, "Error!!  抛出异常信息 ===" + e);

            }
        }
    }
}
