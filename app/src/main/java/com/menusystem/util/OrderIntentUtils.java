package com.menusystem.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.menusystem.activity.OrderActivity;

import static com.menusystem.bean.Verify.RESULET_CODE;

/**
 * Created by ${Kikis} on 2016-08-30.
 */

public class OrderIntentUtils {
    private static final String TAG = "OrderIntentUtils";
    private static Activity context;
    private static String fdname ="";
    private static String fdid ="";
    private static String Sp ="";

    public OrderIntentUtils(Activity context , String fdname, String fdid, String Sp) {
        this.context = context;
        this.fdname=fdname;
        this.fdid=fdid;
        this.Sp=Sp;
    }


    public static void getIntentOrder() {

        Intent intent = new Intent(context, OrderActivity.class);

        intent.putExtra("Food_message_Name", fdname);
        intent.putExtra("Food_message_ID", fdid);
        intent.putExtra("Food_message_SellPrice", Sp);
        context.startActivityForResult(intent, RESULET_CODE);

        Log.i(TAG, " getFoodName " + fdname + "getFoodID" + fdid);

    }
}
