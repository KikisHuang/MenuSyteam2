package com.menusystem.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.menusystem.util.Download;

import java.io.File;

import static com.menusystem.bean.Verify.APK;
import static com.menusystem.bean.Verify.APK_DOWNLOAD;
import static com.menusystem.util.ProgressDialogUtil.closeDialog;
import static com.menusystem.util.ProgressDialogUtil.showDialog;

/**
 * Created by ${Kikis} on 2016-09-28.
 */
public class UpdateVersionAsyncTask extends AsyncTask {

    private static final String TAG = "UpdateVersionAsyncTask";
    private Activity context;
    private File file;

    public UpdateVersionAsyncTask(Activity context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        showDialog(context, "更新中,请稍后。");
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String version = String.valueOf(params[0]);

        return file = Download.downLoadFile(APK_DOWNLOAD +version+APK);
    }

    @Override
    protected void onPostExecute(Object o) {

        if (!file.equals("")) {

            Log.i(TAG, "安装Apk....");
            Download.openFile(context, file);
            closeDialog();

        } else {
            closeDialog();
            Log.i(TAG, "安装失败");

        }

    }
}
