package com.menusystem.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ${Kikis} on 2016-09-19.
 */

public class ProgressDialogUtil {

    private static ProgressDialog progressDialog;

    public static ProgressDialog getProgressDialog(Context context ,ProgressDialog pd , String Msg){

        pd = new ProgressDialog(context,ProgressDialog.THEME_HOLO_LIGHT);

        pd.setMessage(Msg);
        pd.setCancelable(false);
        pd.show();

        return pd;
    }

    public static void showDialog(Context activity, String msg){

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public static void closeDialog(){

        if (progressDialog != null) {
            progressDialog.cancel();
            progressDialog = null;
            System.gc();
        }
    }
}
