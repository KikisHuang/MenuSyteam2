package com.menusystem.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.menusystem.adapter.OrderAdapter;
import com.menusystem.asynctask.PayTheBillAsynctask;
import com.menusystem.asynctask.UpdateVersionAsyncTask;
import com.menusystem.bean.Order;
import com.menusystem.view.SliderChangeView;

import java.util.List;

import static com.menusystem.activity.MainActivity.GainAllPrices;
import static com.menusystem.activity.MainActivity.Send;
import static com.menusystem.activity.Welcome_Activity.Asynctask.Operation;
import static com.menusystem.bean.Verify.NO_PLACE_AN_ORDER;

/**
 * Created by ${Kikis} on 2016-09-06.
 */

public class AlertDialogUtils {


    private static final String TAG = "AlertDialogUtils";

    public static void getPayTheBillDiaglog(final Activity context , final String CSID, final String SystemId, final String TableNumber, List<Order> olist){

      if (olist.size() > 0 && !CSID.equals("")) {
          int G = 0;

       /*   for (int i = 0; i < olist.size(); i++) {

              int Status = olist.get(i).getState();

              if (Status != OFF_THE_STOCKS) {

                  G += 1;
              }
          }*/

//          if (G > 0) {
//
//              AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
//              builder.setTitle("您还有" + G + "道菜没有上噢,是否需要买单?\n(未下单的不收取费用,已下单的根据菜式是否下厨收费。)");
//
//              builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialog,
//                                      int which) {
//
//                      new PayTheBillAsynctask(context).execute(CSID, SystemId, TableNumber);
//
//                  }
//              });
//
//              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//
//                      return;
//                  }
//              });
//              //创建并显示出来
//              builder.create().show();
//
//
//          } else {

              AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
              builder.setTitle("您确定要买单吗? 买单之后将不能继续下单了哦,亲...");

              builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog,
                                      int which) {

                      new PayTheBillAsynctask(context).execute(CSID, SystemId, TableNumber);

                  }
              });

              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                      return;
                  }
              });
              //创建并显示出来
              builder.create().show();


//          }

      } else {
          getOnlyDialog(context,"没有菜式数据无法买单");
          Log.i(TAG, "没有菜式数据..并不能买单..");
          return;
      }
  }


    public static void getDeleteDialog(final Context context, final List<Order> olist, final int position, final SliderChangeView sliderview, final OrderAdapter orderAdapter){


        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);

        builder.setMessage("确定要删除菜式吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {

                if (NO_PLACE_AN_ORDER == olist.get(position).getState()) {

                    Log.i(TAG, "Delete !!!!!!!!菜式名称 ====="+olist.get(position).getFoodName());

                    olist.remove(position);
                    orderAdapter.notifyDataSetChanged();
                    GainAllPrices();
                    return;

                }else{

                    Toast.makeText(context,"已下单的菜品无法删除哦~ 亲 !", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "Not Delete !!!!!!!!!");

                    return;
                }

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        //创建并显示出来
        builder.create().show();
    }

    public static void getCommonDialog(final Context context, final int num){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);


        builder.setMessage("再次点击确认");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                Send(num,context);

                return;

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        //创建并显示出来
        builder.create().show();
    }


    public static void getOnlyDialog(final Context context, String Msg){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);


        builder.setMessage(Msg);

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        //创建并显示出来
        builder.create().show();
    }

    public static void getVersionDialog(final Activity context, final String version){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);


        builder.setMessage("查询到有新版本,请问是否更新?");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {

             new UpdateVersionAsyncTask(context).execute(version);

                return;

            }
        });

        builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Operation(context);
                return;
            }
        });
        //创建并显示出来
        builder.create().show();
    }

}
