package com.menusystem.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.menusystem.asynctask.MessageAsynctask;


/**
 * Created by ${Kikis} on 2016-08-29.
 */

public class AddMessageUtils {
    private static final String TAG = "AddMessageUtils";

    private static String CSID = "";
    private static Context context;
    private static int Num = 0;
    private static String tableNumber = "";


    public AddMessageUtils(String CSID, Context context, int Num, String tableNumber) {

        this.CSID = CSID;
        this.context = context;
        this.Num = Num;
        this.tableNumber = tableNumber;
    }


    public static void SendMessage(String systemId) {

        if (!CSID.isEmpty() && Num != 0 && !tableNumber.isEmpty()) {

            new MessageAsynctask(context).execute(CSID, Num, tableNumber,systemId);

        } else {

            Toast.makeText(context, "没有下单无法使用此功能哦~", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "没有获得订单号, 操作的状态码为: ---- "+ Num);
        }
    }


    public static void NewMeassge(String systemId, Context context, int num, String CSID, String TableNumber) {

        new AddMessageUtils(CSID, context, num, TableNumber);

        SendMessage(systemId);

    }

}
