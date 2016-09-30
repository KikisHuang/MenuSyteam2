package com.menusystem.util;

import android.util.Log;

import com.menusystem.bean.Order;

import java.util.List;

import static com.menusystem.bean.Verify.NO_PLACE_AN_ORDER;

/**
 * Created by ${Kikis} on 2016-09-06.
 */

public class OrderoverlayUtil {


    private static final String TAG = "OrderoverlayUtil";

    public static List OverlayOrderjudge(List<Order> olist, Order od) {

        if(olist.size()>0){


            for (int i = 0; i < olist.size(); i++) {

                if (od.getFoodName().equals(olist.get(i).getFoodName())&&od.getState()==NO_PLACE_AN_ORDER&&NO_PLACE_AN_ORDER==olist.get(i).getState()&&od.getFoodId().equals(olist.get(i).getFoodId())) {

                    olist.get(i).setNumber(od.getNumber() + olist.get(i).getNumber());

                    Log.i(TAG,"菜单中有重复的菜品,进行数量叠加");
                    return olist;
                }

            }

            olist.add(od);
            Log.i(TAG,"菜单中没有重复的菜品,正常操作");
            return olist;
        }else{

            olist.add(od);
            Log.i(TAG,"菜单中没有重复的菜品,正常操作");
            return olist;
        }

    }
}
