package com.menusystem.util;

import android.util.Log;

import com.menusystem.bean.Data;
import com.menusystem.bean.Three;

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
//        int THREE = 3;


        List<Three> alist;
        List<Data> all = new ArrayList<>();

        int num = 0;
        String value = "";

        for (int i = 0; i < dlist.size(); i++) {

            if (!value.equals(dlist.get(i).getFoodTypeID())) {

                List<Data> clist = new ArrayList<>();
                num = 0;
                value = dlist.get(i).getFoodTypeID();
                for (int j = 0; j < dlist.size(); j++) {

                    if (value.equals(dlist.get(j).getFoodTypeID())) {
//                        clist.add(dlist.get(j).getFoodTypeID());
                        Data data = new Data();
                        data.setFoodID(dlist.get(j).getFoodID());
                        data.setFoodPicAddress(dlist.get(j).getFoodPicAddress());
                        data.setSystemID(dlist.get(j).getSystemID());
                        data.setFoodName(dlist.get(j).getFoodName());
                        data.setSellPrice(dlist.get(j).getSellPrice());
                        data.setFoodTypeID(dlist.get(j).getFoodTypeID());

                        clist.add(data);

                        num++;
                    }
                }
//				System.out.println("值 =="+value+"总数=="+num);
                if (num % 3 == 0) {
                    for (int s = 0; s < clist.size(); s++) {

                        all.add(clist.get(s));
                    }

                } else {

                    int b = 3;
                    int c = num % b;

                    int d = b - c;
                    for (int l = 0; l < d; l++) {
                        Data data = new Data();

                        data.setFoodTypeID(value);
                        data.setSellPrice("");
                        data.setFoodName("");
                        data.setSystemID("");
                        data.setFoodID("");
                        data.setFoodPicAddress("");

                        clist.add(data);

                    }
                    Log.i(TAG,value+" 分类新添 "+ d +"条数据");
                    for (int m = 0; m < clist.size(); m++) {

                        all.add(clist.get(m));
                    }

                }
            }

        }

//        for (int o = 0; o < all.size(); o++) {
//
//            System.out.println(all.get(o));
//        }

        Log.i(TAG,"旧集合长度 =="+dlist.size()+"新集合长度 ==" + all.size());
        Log.i(TAG," all.size() % 3 = " + all.size() % 3);


        //判断从服务端获得的数据%3是否=0;
        if (all.size() % 3 == 0) {

            alist = new ArrayList<Three>();

            for (int i = 0; i < all.size(); i += 3) {

                Three a = new Three();

                a.setFoodName1(all.get(i).getFoodName());
                a.setFoodName2(all.get(i + ONE).getFoodName());
                a.setFoodName3(all.get(i + TWO).getFoodName());

                a.setFoodID1(all.get(i).getFoodID());
                a.setFoodID2(all.get(i + ONE).getFoodID());
                a.setFoodID3(all.get(i + TWO).getFoodID());

                a.setFoodPicAddress1(all.get(i).getFoodPicAddress());
                a.setFoodPicAddress2(all.get(i + ONE).getFoodPicAddress());
                a.setFoodPicAddress3(all.get(i + TWO).getFoodPicAddress());

                a.setFoodTypeID1(all.get(i).getFoodTypeID());
                a.setFoodTypeID2(all.get(i + ONE).getFoodTypeID());
                a.setFoodTypeID3(all.get(i + TWO).getFoodTypeID());


                a.setSellPrice1(all.get(i).getSellPrice());
                a.setSellPrice2(all.get(i + ONE).getSellPrice());
                a.setSellPrice3(all.get(i + TWO).getSellPrice());

                alist.add(a);
//                System.out.println("one=" + a.getFoodID1() + "two=" + a.getFoodID2() + "three=" + a.getFoodID3());

//                Log.i(TAG, MY_LOGCAT_TAG + "能够整除..dlist.size = " + dlist.size());
            }
            //判断是否成功分配完成数据,成功return;

            if (alist.size() > 0) {

                for (int i = 0; i < alist.size(); i++) {

                    Log.i(TAG, MY_LOGCAT_TAG + "输出分配的alist ---- " + alist.get(i));

                }

                return alist;
            }

        }
//        else {
//
//            //如果不能%3先取余数;
//            int residue = dlist.size() % 3;
//            Log.i(TAG, MY_LOGCAT_TAG + "余数=" + residue);
//
//            //再得到-余数后能够%4的长度;
//            int blist = dlist.size() - residue;
//
//            Log.i(TAG, MY_LOGCAT_TAG + "取整=" + blist);
//
//            alist = new ArrayList<Three>();
//
//            //分配给alist
//            for (int i = 0; i < blist; i += 3) {
//
//                Three a = new Three();
//
//                a.setFoodName1(dlist.get(i).getFoodName());
//                a.setFoodName2(dlist.get(i + 1).getFoodName());
//                a.setFoodName3(dlist.get(i + 2).getFoodName());
//
//                a.setFoodID1(dlist.get(i).getFoodID());
//                a.setFoodID2(dlist.get(i + 1).getFoodID());
//                a.setFoodID3(dlist.get(i + 2).getFoodID());
//
//                a.setFoodPicAddress1(dlist.get(i).getFoodPicAddress());
//                a.setFoodPicAddress2(dlist.get(i + 1).getFoodPicAddress());
//                a.setFoodPicAddress3(dlist.get(i + 2).getFoodPicAddress());
//
//                a.setFoodTypeID1(dlist.get(i).getFoodTypeID());
//                a.setFoodTypeID2(dlist.get(i + 1).getFoodTypeID());
//                a.setFoodTypeID3(dlist.get(i + 2).getFoodTypeID());
//
//
//                a.setSellPrice1(dlist.get(i).getSellPrice());
//                a.setSellPrice2(dlist.get(i + 1).getSellPrice());
//                a.setSellPrice3(dlist.get(i + 2).getSellPrice());
//
//
//                alist.add(a);
//
////                System.out.println("one=" + a.getFoodID1() + "two=" + a.getFoodID2() + "three=" + a.getFoodID3());
//
////                Log.i(TAG, MY_LOGCAT_TAG + "one=" + alist.get(i).getFoodID1());
//
//            }
//            //判断余数做出相应操作
//            if (residue == 1) {
//
//                Three a = new Three();
//
//                a.setFoodName1(dlist.get(dlist.size() - ONE).getFoodName());
//                a.setFoodName2("");
//                a.setFoodName3("");
//
//                a.setFoodID1(dlist.get(dlist.size() - ONE).getFoodID());
//                a.setFoodID2("");
//                a.setFoodID3("");
//
//                a.setFoodPicAddress1(dlist.get(dlist.size() - ONE).getFoodPicAddress());
//                a.setFoodPicAddress2("");
//                a.setFoodPicAddress3("");
//
//                a.setFoodTypeID1(dlist.get(dlist.size() - ONE).getFoodTypeID());
//                a.setFoodTypeID2("");
//                a.setFoodTypeID3("");
//
//
//                a.setSellPrice1(dlist.get(dlist.size() - ONE).getSellPrice());
//                a.setSellPrice2("");
//                a.setSellPrice3("");
//
//
//                alist.add(a);
//
////                System.out.println("residue==1===" + alist.get(alist.size() - 1).foodName1);
//
//                Log.i(TAG, MY_LOGCAT_TAG + "residue==1===" + alist.get(alist.size() - 1).getFoodID1());
//            }
//            //判断余数做出相应操作
//            if (residue == 2) {
//                Three a = new Three();
//
//                a.setFoodName1(dlist.get(dlist.size() - ONE).getFoodName());
//                a.setFoodName2(dlist.get(dlist.size() - TWO).getFoodName());
//                a.setFoodName3("");
//
//                a.setFoodID1(dlist.get(dlist.size() - ONE).getFoodID());
//                a.setFoodID2(dlist.get(dlist.size() - TWO).getFoodID());
//                a.setFoodID3("");
//
//                a.setFoodPicAddress1(dlist.get(dlist.size() - ONE).getFoodPicAddress());
//                a.setFoodPicAddress2(dlist.get(dlist.size() - TWO).getFoodPicAddress());
//                a.setFoodPicAddress3("");
//
//                a.setFoodTypeID1(dlist.get(dlist.size() - ONE).getFoodTypeID());
//                a.setFoodTypeID2(dlist.get(dlist.size() - TWO).getFoodTypeID());
//                a.setFoodTypeID3("");
//
//
//                a.setSellPrice1(dlist.get(dlist.size() - ONE).getSellPrice());
//                a.setSellPrice2(dlist.get(dlist.size() - TWO).getSellPrice());
//                a.setSellPrice3("");
//
//
//                alist.add(a);
////                System.err.println("residue==2===" + alist.get(alist.size() - 1).foodName1 + "-----" + alist.get(alist.size() - 1).foodName2);
//
//
//                Log.i(TAG, MY_LOGCAT_TAG + "residue==2===" + alist.get(alist.size() - 1).getFoodID1() + "---" + alist.get(alist.size() - 1).getFoodID2());
//            }
//
//            for (int i = 0; i < alist.size(); i++) {
//
//                Log.i(TAG, MY_LOGCAT_TAG + "输出分配的slist ---- " + alist.get(i));
//
//            }
//
//            if (alist.size() > 0) {
//
//
//                Log.i(TAG, MY_LOGCAT_TAG + "输出长度 ---- " + alist.size());
//                return alist;
//
//            }
//
//
//        }
        return null;
    }


}
