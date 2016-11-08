package com.menusystem.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.menusystem.activity.PayTheBillActivity;
import com.menusystem.util.AlertDialogUtils;
import com.menusystem.util.HttpURlConnection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.PAYTHEBILL;
import static com.menusystem.bean.Verify.VERIFY_KEY;

/**
 * Created by ${Kikis} on 2016-08-19.
 */
public class PayTheBillAsynctask extends AsyncTask {
    private static final String TAG = "PayTheBillAsynctask";
    String result = "";
    Activity Ac;
    String CSID = "";
    String Number = "";

    public PayTheBillAsynctask(Activity Ac) {

        this.Ac = Ac;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String menukey = VERIFY_KEY;

         CSID = String.valueOf(params[0]);
         String SysId = String.valueOf(params[1]);
         Number = String.valueOf(params[2]);

        Log.i(TAG, "买单的CSID ===" + CSID);
        StringBuilder sb = new StringBuilder(MHTTP + PAYTHEBILL);

        sb.append("?");
        try {
            sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

            sb.append("CSID=" + URLEncoder.encode(CSID.toString(), "UTF-8") + "&");

            sb.append("SystemID=" + URLEncoder.encode(SysId.toString(), "UTF-8") + "&");

            sb.append("TableNumber=" + URLEncoder.encode(Number.toString(), "UTF-8") + "&");

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
            if (o.equals("true")) {
//                AlertDialogUtils.getOnlyDialog(Ac,"亲爱的顾客,请稍等...已经为您联系服务员前来结账...");

//                Toast.makeText(Ac, "亲爱的顾客,请稍等...已经为您联系服务员前来结账...", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "成功修改数据库  result ===" + o);

//                ORDERSUCCEED = false;
//                PAY_THE_BILL = true;
//                DataJudge(Ac);

                Intent intent  = new Intent(Ac,PayTheBillActivity.class);

                intent.putExtra("Pay_csid",CSID);

                Ac.startActivity(intent);

//                Intent intent = new Intent(Ac, EndActivity.class);
//
//                Ac.startActivity(intent);

//                CommonUtil.Exit(Ac);

            }
            if (o.equals("false")) {

                AlertDialogUtils.getOnlyDialog(Ac,"亲爱的顾客,订单无法结账,请您自行联系服务员来结账!");
                Log.i(TAG, "无法结账   result ===" + o);

                return;
            }
        } catch (Exception e) {

            AlertDialogUtils.getOnlyDialog(Ac,"与服务器连接异常...请您自行联系服务员结账!...");
            Log.i(TAG, "Error ===" + e);
            return;
        }

    }
}
