package com.menusystem.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.menusystem.util.LogUtils.PrintError;

/**
 * downLoadFile
 *
 * @author Kikis
 *         2016-09-28
 *         版本更新;
 */
public class Download {

    private static final String TAG = "Download";

    //下载apk;
    public static File downLoadFile(String httpUrl) {
        // TODO Auto-generated method stub  
        final String fileName = "SystemMenu.apk";
        File tmpFile = new File("//sdcard");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("//sdcard//" + fileName);
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
//                              Toast.makeText(DownFile.this, "连接超时。。。。。", Toast.LENGTH_SHORT).show();
                    Log.i("time", "连接超时。。。。。");
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                PrintError(TAG, e);
                return null;
            }
        } catch (MalformedURLException e) {
            PrintError(TAG, e);
            return null;
        }
        return file;
    }

    //安装apk;
    public static void openFile(Context context, File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
        System.exit(0);
    }
}
