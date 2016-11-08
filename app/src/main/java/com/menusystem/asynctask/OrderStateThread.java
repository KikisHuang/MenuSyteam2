package com.menusystem.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.menusystem.activity.EndActivity;
import com.menusystem.activity.MainActivity;
import com.menusystem.activity.PayTheBillActivity;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.HttpURlConnection;
import com.menusystem.util.LogUtils;

import java.net.URLEncoder;

import static com.menusystem.activity.MainActivity.DataJudge;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.ORDERSUCCEED;
import static com.menusystem.bean.Verify.PAY_QUERY_THREAD;
import static com.menusystem.bean.Verify.PAY_THE_BILL;
import static com.menusystem.bean.Verify.VERIFY_KEY;

/**
 * Created by ${Kikis} on 2016-10-24.
 */
public class OrderStateThread extends Thread {

    public static final String TAG = "OrderStateThread";
    private boolean Pay_Flag = false;

    String CSID = "";
    Activity ac;

    public OrderStateThread(String csid, PayTheBillActivity payTheBillActivity) {
        this.CSID = csid;
        this.ac = payTheBillActivity;
    }

    @Override
    public void run() {

        while (!Pay_Flag) {

            Log.i(TAG, "启动询结账状态查询线程.....");
            try {
                Thread.sleep(8000);


                String menukey = VERIFY_KEY;

                StringBuilder sb = new StringBuilder(MHTTP + PAY_QUERY_THREAD);

                sb.append("?");

                sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");
                sb.append("CSID=" + URLEncoder.encode(CSID.toString(), "UTF-8") + "&");

                String Result = HttpURlConnection.getHttpURlConnection(sb);

                if (Result.equals("false")) {

                    Log.i(TAG, "未结账  Result ==" + Result);

                }
                if (Result.equals("true")) {
                    Log.i(TAG, "已结账 Result ==" + Result);
                    Pay_Flag = true;
                    ORDERSUCCEED = false;
                    PAY_THE_BILL = true;
                    DataJudge(ac);
                    Intent intent = new Intent(ac, EndActivity.class);
                    CommonUtil.Exit(ac);
                    ac.startActivity(intent);
                    MainActivity.instance.finish();
                }

            } catch (Exception e) {

                LogUtils.PrintError(TAG, e);
            }


        }
    }
}
