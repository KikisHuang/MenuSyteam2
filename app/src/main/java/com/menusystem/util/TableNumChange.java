package com.menusystem.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.menusystem.R;
import com.menusystem.asynctask.CommonAsynctask;
import com.menusystem.bean.Order;
import com.menusystem.interfaces.AsyncResponse;

import java.util.List;

import static com.menusystem.activity.MainActivity.SetTableNum;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.QUERY_TABLE_NUMBER;

/**
 * Created by ${Kikis} on 2016-08-31.
 */

public class TableNumChange {


    private static final String TAG = "TableNumChange";

    public static void getTableAlertDialog(final Activity context, final List<Order> olist) {

        LayoutInflater factory = LayoutInflater.from(context);//提示框
        final View view = factory.inflate(R.layout.edittext_layout, null);//这里必须是final的
        final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象
        edit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
        new AlertDialog.Builder(context)
                .setTitle("输入管理员密码")//提示框标题
                .setView(view)
                .setPositiveButton("确定",//提示框的两个按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                String value1 = edit.getText().toString();

                                if (value1.equals("8888")) {


                                    if (olist.isEmpty()) {


                                        LayoutInflater factory = LayoutInflater.from(context);//提示框
                                        final View view = factory.inflate(R.layout.edittext_layout, null);//这里必须是final的
                                        final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象


                                        new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                                                .setTitle("输入更换的桌号")//提示框标题
                                                .setView(view)
                                                .setPositiveButton("确定",//提示框的两个按钮
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog,
                                                                                int which) {

                                                                final String value2 = edit.getText().toString();

                                                                if (!value2.isEmpty() && olist.isEmpty()) {

                                                                    CommonAsynctask Comm = (CommonAsynctask) new CommonAsynctask(context).execute(value2, MHTTP + QUERY_TABLE_NUMBER);

                                                                    Comm.setOnAsyncResponse(new AsyncResponse() {
                                                                        @Override
                                                                        public void onReceivedSuccess() {

                                                                            SetTableNum(context, value2);
                                                                            Log.i(TAG, "onReceivedSuccess 接口方法调用,桌号修改成功");
                                                                        }

                                                                        @Override
                                                                        public void onReceivedFailed() {


                                                                            Log.i(TAG, "onReceivedFailed 接口方法调用,桌号修改失败");

                                                                            return;

                                                                        }
                                                                    });

                                                                } else {
                                                                    Toast.makeText(context, "桌号不能为空", Toast.LENGTH_SHORT).show();

                                                                    return;
                                                                }
                                                            }
                                                        }).setNegativeButton("取消", null).create().show();

                                    } else {

                                        Toast.makeText(context, "菜单已有菜品,无法修改桌号,请删除后再做修改操作!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                } else {

                                    Toast.makeText(context, "密码错误!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("取消", null).create().show();

    }


}
