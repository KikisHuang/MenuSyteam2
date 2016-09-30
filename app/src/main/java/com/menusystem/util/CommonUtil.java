package com.menusystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.menusystem.application.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.menusystem.util.LogUtils.PrintError;

/**
 * Created by ${Kikis} on 2016-07-22.
 */
public class CommonUtil {

    private static final String TAG = "CommonUtil";

    /**
     * 无标题栏
     */

    public static void setNoTitleBar(Activity context) {

        context.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * 全屏效果
     */

    public static void setFullScreen(Activity context) {

        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 退出回收内存
     */
    public static void Exit(Activity context) {

        context.finish();
        System.gc();

    }


    /**
     * 完全退出App方法
     */
    public static void entirelyexit() {
        System.exit(0);
    }

    //addActivity;
    public static void addActivity(Activity ac) {
        MyApplication.getInstance().addActivity(ac);
    }

    //destroyActivity;
    public static void destroyActivity() {

        MyApplication.getInstance().destroy();
    }

    public static void setTextViewStyle(TextView view, LinearLayout mLayout, String text) {

//        // 第一个参数为宽的设置，第二个参数为高的设置。
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                55));

        //首先定义个LayoutParams，然后在设置margin，在把这个LayoutParams设置给控件
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75);
        layoutParams.setMargins(3, 3, 3, 15);//4个参数按顺序分别是左上右下
        //mTextView的Margins
        view.setLayoutParams(layoutParams);


        // 设置mTextView的文字
        view.setText(text);
        // 设置字体大小
        view.setTextSize(13);
        // 设置背景
//        view.setBackgroundColor(Color.BLUE);
        // 设置字体颜色
        view.setTextColor(Color.BLACK);
        //设置居中
        view.setGravity(Gravity.CENTER);
        //
//        view.setPadding(1, 0, 0, 0);//left, top, right, bottom

        // 将TextView添加到Linearlayout中去
        mLayout.addView(view);

    }

    /**
     * left Menu TextView Style
     */
    public static void setTextView(TextView view, String text) {

//        // 第一个参数为宽的设置，第二个参数为高的设置。
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                55));

        //首先定义个LayoutParams，然后在设置margin，在把这个LayoutParams设置给控件
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75);
        layoutParams.setMargins(3, 3, 3, 10);//4个参数按顺序分别是左上右下
        //mTextView的Margins
        view.setLayoutParams(layoutParams);


        // 设置mTextView的文字
        view.setText(text);
        // 设置字体大小
        view.setTextSize(17);
        // 设置背景
//        view.setBackgroundColor(Color.BLUE);
        // 设置字体颜色
        view.setTextColor(Color.BLACK);
        //设置居中
        view.setGravity(Gravity.CENTER);
        //
//        view.setPadding(1, 0, 0, 0);//left, top, right, bottom
    }


    /**
     * 联网权限判断
     */
    public static NetworkInfo getNetworkInfo(Activity context) {

        //判断是否联网
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        return info;
    }


    /**
     * 获得系统当前年-月-日-时-分-秒
     */
    public static String getDateAndTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        return formatter.format(curDate);
    }

    //年月日
    public static String getDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        return formatter.format(curDate);
    }

    //时分秒
    public static String getTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        return formatter.format(curDate);
    }

    //版本查询
    public static String getVersion(Context context) {

        try {
            PackageManager manager = context.getPackageManager();

            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            String version = info.versionName;

            return version;

        } catch (Exception e) {

            e.printStackTrace();
            PrintError(TAG,e);
        }

        return null;
    }
}
