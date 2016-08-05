package com.menusystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ${Kikis} on 2016-07-22.
 */
public class CommonUtil {

    /**
     *无标题栏
     */

    public static void setNoTitleBar(Activity context) {

        context.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     *  全屏效果
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
    public static void entirelyexit(Activity ac) {

        Intent intent = new Intent(Intent.ACTION_MAIN);

        intent.addCategory(Intent.CATEGORY_HOME);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ac.startActivity(intent);
        System.exit(0);

    }

    public static void setTextViewStyle(TextView view, LinearLayout mLayout,String text) {

//        // 第一个参数为宽的设置，第二个参数为高的设置。
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                55));

        //首先定义个LayoutParams，然后在设置margin，在把这个LayoutParams设置给控件
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75);
        layoutParams.setMargins(3,3,3,15);//4个参数按顺序分别是左上右下
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
        layoutParams.setMargins(3,3,3,10);//4个参数按顺序分别是左上右下
        //mTextView的Margins
        view.setLayoutParams(layoutParams);


        // 设置mTextView的文字
        view.setText(text);
        // 设置字体大小
        view.setTextSize(11);
        // 设置背景
//        view.setBackgroundColor(Color.BLUE);
        // 设置字体颜色
        view.setTextColor(Color.BLACK);
        //设置居中
        view.setGravity(Gravity.CENTER);
        //
//        view.setPadding(1, 0, 0, 0);//left, top, right, bottom
    }


/**联网权限判断
 */
    public static NetworkInfo getNetworkInfo(Activity context){

        //判断是否联网
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        return info;
    }
}
