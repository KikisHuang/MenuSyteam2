package com.menusystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ${Kikis} on 2016-08-15.
 * <p>
 * SharedPreference 方法封装
 */

public class SharedPreference {

    private static final String TAG ="SharedPreference" ;
    public static SharedPreferences sharedPreferences;

    /**
     * 存储sharedpreferences
     */
    public static void setSharedPreference(Activity ac, String Name, String Content) {

        sharedPreferences = ac.getSharedPreferences("Kikis", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, Content);
        editor.commit();// 提交修改
        Log.i(TAG,"SharedPreference 存入TableNumber数据");
    }
    /**
     * 清除sharedpreferences的数据
     */
    public static void removeSharedPreference(Activity ac, String Name) {

        Log.i(TAG,"清除sharedpreferences的数据");
        sharedPreferences = ac.getSharedPreferences("Kikis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Name);
        editor.commit();// 提交修改

    }
    /**
     * 获得sharedpreferences的数据
     */

    public static String getSahrePreference(Activity ac, String Name) {

        sharedPreferences = ac.getSharedPreferences("Kikis", Context.MODE_PRIVATE);

        try {
            return sharedPreferences.getString(Name, "");

        } catch (Exception e) {

            return "";
        }

    }


}
