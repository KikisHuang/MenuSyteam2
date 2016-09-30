package com.menusystem.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.menusystem.util.HttpURlConnection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.menusystem.bean.Verify.MESSAGE;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.VERIFY_KEY;

/**
 * Created by ${Kikis} on 2016-08-29.
 */
public class MessageAsynctask extends AsyncTask {


    private final Context context;

    public MessageAsynctask(Context context) {
        this.context = context;
    }

    private static final String TAG = "MessageAsynctask";
    String result = "";

    @Override
    protected Object doInBackground(Object[] params) {

        String menukey = VERIFY_KEY;

        String CSID = String.valueOf(params[0]);
        int Num = (int) params[1];
        String table = String.valueOf(params[2]);
        String systemId = String.valueOf(params[3]);

        Log.i(TAG, "传递服务台消息状态码为 ===" + Num);
        Log.i(TAG, "传递服务台CSID为 ===" + CSID);
        Log.i(TAG, "传递服务台桌号为 ===" + table);
        Log.i(TAG, "传递服务台系统id为 ===" + systemId);

        StringBuilder sb = new StringBuilder(MHTTP + MESSAGE);

        sb.append("?");

        try {
            sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

            sb.append("CSID=" + URLEncoder.encode(CSID.toString(), "UTF-8") + "&");

            sb.append("SystemID=" + URLEncoder.encode(systemId.toString(), "UTF-8") + "&");

            sb.append("TableNumber=" + URLEncoder.encode(table.toString(), "UTF-8") + "&");

            sb.append("Num=" + URLEncoder.encode(String.valueOf(Num), "UTF-8") + "&");

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

            String result = String.valueOf(o);

            if (result.equals("true")) {

                Toast.makeText(context, "已成功呼叫服务员,请稍等", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "result ---- "+result);

                return;
            }
            if (result.equals("false")) {

                Toast.makeText(context, "呼叫服务员失败,请自行联系服务员", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "result ---- "+result);

                return;
            }

        } catch (Exception e) {

            Toast.makeText(context, "无法与服务器端获得连接,请联系服务员", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "无法与服务器端获得连接 打印错误信息-----------------" + e);

            return;
        }
    }
}
