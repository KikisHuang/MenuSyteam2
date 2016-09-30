package com.menusystem.util;

import android.util.Log;

import com.menusystem.bean.Data;
import com.menusystem.bean.Four;

import java.util.ArrayList;
import java.util.List;

import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;

/**
 * Created by ${Kikis} on 2016-08-08.
 * <p>
 * 用于数据分类匹配adapter中的item
 */

public class Partition {

    public static final String TAG = "Partitino";


    public static List GetDataSwitch(List<Data> dlist) {

        int ONE = 1;
        int TWO = 2;
        int THREE = 3;


        List<Four> alist;


        //判断从服务端获得的数据%4是否=0;
        if (dlist.size() % 4 == 0) {

            alist = new ArrayList<Four>();

            for (int i = 0; i < dlist.size(); i += 4) {

                Four a = new Four();

                a.setFoodName1(dlist.get(i).getFoodName());
                a.setFoodName2(dlist.get(i + ONE).getFoodName());
                a.setFoodName3(dlist.get(i + TWO).getFoodName());
                a.setFoodName4(dlist.get(i + THREE).getFoodName());

                a.setFoodID1(dlist.get(i).getFoodID());
                a.setFoodID2(dlist.get(i + ONE).getFoodID());
                a.setFoodID3(dlist.get(i + TWO).getFoodID());
                a.setFoodID4(dlist.get(i + THREE).getFoodID());

                a.setFoodPicAddress1(dlist.get(i).getFoodPicAddress());
                a.setFoodPicAddress2(dlist.get(i + ONE).getFoodPicAddress());
                a.setFoodPicAddress3(dlist.get(i + TWO).getFoodPicAddress());
                a.setFoodPicAddress4(dlist.get(i + THREE).getFoodPicAddress());

                a.setFoodTypeID1(dlist.get(i).getFoodTypeID());
                a.setFoodTypeID2(dlist.get(i + ONE).getFoodTypeID());
                a.setFoodTypeID3(dlist.get(i + TWO).getFoodTypeID());
                a.setFoodTypeID4(dlist.get(i + THREE).getFoodTypeID());


                a.setSellPrice1(dlist.get(i).getSellPrice());
                a.setSellPrice2(dlist.get(i + ONE).getSellPrice());
                a.setSellPrice3(dlist.get(i + TWO).getSellPrice());
                a.setSellPrice4(dlist.get(i + THREE).getSellPrice());

                alist.add(a);
//                System.out.println("one=" + a.getFoodID1() + "two=" + a.getFoodID2() + "three=" + a.getFoodID3());

                Log.i(TAG, MY_LOGCAT_TAG +"能够整除..dlist.size = "+dlist.size());
            }
            //判断是否成功分配完成数据,成功return;

            if (alist.size() > 0) {

                for (int i = 0; i < alist.size(); i++) {

                    Log.i(TAG, MY_LOGCAT_TAG + "输出分配的slist ---- " + alist.get(i));

                }

                return alist;
            }

        } else {

            //如果不能%4先取余数;
            int residue = dlist.size() % 4;
            Log.i(TAG, MY_LOGCAT_TAG + "余数=" + residue);

            //再得到-余数后能够%4的长度;
            int blist = dlist.size() - residue;

            Log.i(TAG, MY_LOGCAT_TAG + "取整=" + blist);

            alist = new ArrayList<Four>();

            //分配给alist
            for (int i = 0; i < blist; i += 4) {

                Four a = new Four();

                a.setFoodName1(dlist.get(i).getFoodName());
                a.setFoodName2(dlist.get(i + 1).getFoodName());
                a.setFoodName3(dlist.get(i + 2).getFoodName());
                a.setFoodName4(dlist.get(i + 3).getFoodName());

                a.setFoodID1(dlist.get(i).getFoodID());
                a.setFoodID2(dlist.get(i + 1).getFoodID());
                a.setFoodID3(dlist.get(i + 2).getFoodID());
                a.setFoodID4(dlist.get(i + 3).getFoodID());

                a.setFoodPicAddress1(dlist.get(i).getFoodPicAddress());
                a.setFoodPicAddress2(dlist.get(i + 1).getFoodPicAddress());
                a.setFoodPicAddress3(dlist.get(i + 2).getFoodPicAddress());
                a.setFoodPicAddress4(dlist.get(i + 3).getFoodPicAddress());

                a.setFoodTypeID1(dlist.get(i).getFoodTypeID());
                a.setFoodTypeID2(dlist.get(i + 1).getFoodTypeID());
                a.setFoodTypeID3(dlist.get(i + 2).getFoodTypeID());
                a.setFoodTypeID4(dlist.get(i + 3).getFoodTypeID());


                a.setSellPrice1(dlist.get(i).getSellPrice());
                a.setSellPrice2(dlist.get(i + 1).getSellPrice());
                a.setSellPrice3(dlist.get(i + 2).getSellPrice());
                a.setSellPrice4(dlist.get(i + 3).getSellPrice());


                alist.add(a);

//                System.out.println("one=" + a.getFoodID1() + "two=" + a.getFoodID2() + "three=" + a.getFoodID3());

//                Log.i(TAG, MY_LOGCAT_TAG + "one=" + alist.get(i).getFoodID1());

            }
            //判断余数做出相应操作
            if (residue == 1) {

                Four a = new Four();

                a.setFoodName1(dlist.get(dlist.size() - ONE).getFoodName());
                a.setFoodName2("");
                a.setFoodName3("");
                a.setFoodName4("");

                a.setFoodID1(dlist.get(dlist.size() - ONE).getFoodID());
                a.setFoodID2("");
                a.setFoodID3("");
                a.setFoodID4("");

                a.setFoodPicAddress1(dlist.get(dlist.size() - ONE).getFoodPicAddress());
                a.setFoodPicAddress2("");
                a.setFoodPicAddress3("");
                a.setFoodPicAddress4("");

                a.setFoodTypeID1(dlist.get(dlist.size() - ONE).getFoodTypeID());
                a.setFoodTypeID2("");
                a.setFoodTypeID3("");
                a.setFoodTypeID4("");


                a.setSellPrice1(dlist.get(dlist.size() - ONE).getSellPrice());
                a.setSellPrice2("");
                a.setSellPrice3("");
                a.setSellPrice4("");


                alist.add(a);

//                System.out.println("residue==1===" + alist.get(alist.size() - 1).foodName1);

                Log.i(TAG, MY_LOGCAT_TAG + "residue==1===" + alist.get(alist.size() - 1).getFoodID1());
            }
            //判断余数做出相应操作
            if (residue == 2) {
                Four a = new Four();

                a.setFoodName1(dlist.get(dlist.size() - ONE).getFoodName());
                a.setFoodName2(dlist.get(dlist.size() - TWO).getFoodName());
                a.setFoodName3("");
                a.setFoodName4("");

                a.setFoodID1(dlist.get(dlist.size() - ONE).getFoodID());
                a.setFoodID2(dlist.get(dlist.size() - TWO).getFoodID());
                a.setFoodID3("");
                a.setFoodID4("");

                a.setFoodPicAddress1(dlist.get(dlist.size() - ONE).getFoodPicAddress());
                a.setFoodPicAddress2(dlist.get(dlist.size() - TWO).getFoodPicAddress());
                a.setFoodPicAddress3("");
                a.setFoodPicAddress4("");

                a.setFoodTypeID1(dlist.get(dlist.size() - ONE).getFoodTypeID());
                a.setFoodTypeID2(dlist.get(dlist.size() - TWO).getFoodTypeID());
                a.setFoodTypeID3("");
                a.setFoodTypeID4("");


                a.setSellPrice1(dlist.get(dlist.size() - ONE).getSellPrice());
                a.setSellPrice2(dlist.get(dlist.size() - TWO).getSellPrice());
                a.setSellPrice3("");
                a.setSellPrice4("");


                alist.add(a);
//                System.err.println("residue==2===" + alist.get(alist.size() - 1).foodName1 + "-----" + alist.get(alist.size() - 1).foodName2);


                Log.i(TAG, MY_LOGCAT_TAG + "residue==2===" + alist.get(alist.size() - 1).getFoodID1() + "---" + alist.get(alist.size() - 1).getFoodID2());
            }

            if (residue == 3) {
                Four a = new Four();

                a.setFoodName1(dlist.get(dlist.size() - ONE).getFoodName());
                a.setFoodName2(dlist.get(dlist.size() - TWO).getFoodName());
                a.setFoodName3(dlist.get(dlist.size() - THREE).getFoodName());
                a.setFoodName4("");

                a.setFoodID1(dlist.get(dlist.size() - ONE).getFoodID());
                a.setFoodID2(dlist.get(dlist.size() - TWO).getFoodID());
                a.setFoodID3(dlist.get(dlist.size() - THREE).getFoodID());
                a.setFoodID4("");

                a.setFoodPicAddress1(dlist.get(dlist.size() - ONE).getFoodPicAddress());
                a.setFoodPicAddress2(dlist.get(dlist.size() - TWO).getFoodPicAddress());
                a.setFoodPicAddress3(dlist.get(dlist.size() - THREE).getFoodPicAddress());
                a.setFoodPicAddress4("");

                a.setFoodTypeID1(dlist.get(dlist.size() - ONE).getFoodTypeID());
                a.setFoodTypeID2(dlist.get(dlist.size() - TWO).getFoodTypeID());
                a.setFoodTypeID3(dlist.get(dlist.size() - THREE).getFoodTypeID());
                a.setFoodTypeID4("");


                a.setSellPrice1(dlist.get(dlist.size() - ONE).getSellPrice());
                a.setSellPrice2(dlist.get(dlist.size() - TWO).getSellPrice());
                a.setSellPrice3(dlist.get(dlist.size() - THREE).getSellPrice());
                a.setSellPrice4("");


                alist.add(a);
//                System.err.println("residue==2===" + alist.get(alist.size() - 1).foodName1 + "-----" + alist.get(alist.size() - 1).foodName2);


                Log.i(TAG, MY_LOGCAT_TAG + "residue==2===" + alist.get(alist.size() - 1).getFoodID1() + "---" + alist.get(alist.size() - 1).getFoodID2());
            }

            for (int i = 0; i < alist.size(); i++) {

                Log.i(TAG, MY_LOGCAT_TAG + "输出分配的slist ---- " + alist.get(i));

            }

            if (alist.size() > 0) {


                Log.i(TAG, MY_LOGCAT_TAG + "输出长度 ---- " + alist.size());
                return alist;

            }


        }
        return null;
    }


}
