package com.menusystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.menusystem.R;
import com.menusystem.bean.Order;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.HttpURlConnection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.menusystem.bean.Verify.FOOD_STATE_HHTP;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.NO_PLACE_AN_ORDER;
import static com.menusystem.bean.Verify.ORDERFLAG;
import static com.menusystem.bean.Verify.RESULET_CODE;
import static com.menusystem.bean.Verify.VERIFY_KEY;
import static com.menusystem.util.CommonUtil.addActivity;

/**
 * Created by ${Kikis} on 2016-08-11.
 */

public class OrderActivity extends Activity {
    private TextView cancel, submit, order_food_name, order_food_price, minus, add, edt, shouwan, jiage, close;
    private ImageView Out_Of_Print;
    private String FoodName, FoodId, FoodSp;
    private RelativeLayout add_layout;
    public static final String TAG = "OrderActivity";
    public List<Order> olist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        addActivity(this);
        init();
        receiver();
        setMessage();
    }

    private void setMessage() {

        if (!FoodName.equals("") && !FoodId.equals("") && !FoodSp.equals("")) {

            new FoodStateAsyck().execute(FoodName, FoodId);

        } else {

            Log.i(TAG, "没有获得数据!!!!!!!!!!!");

            CommonUtil.Exit(OrderActivity.this);
        }
    }

    private void receiver() {

        FoodName = getIntent().getStringExtra("Food_message_Name");
        FoodId = getIntent().getStringExtra("Food_message_ID");
        FoodSp = getIntent().getStringExtra("Food_message_SellPrice");


    }


    private void closeClick() {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtil.Exit(OrderActivity.this);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtil.Exit(OrderActivity.this);
            }
        });


    }

    private void click() {


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("您确定添加吗?");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        int Number = Integer.parseInt(edt.getText().toString());

                        Order od = new Order();

                        od.setFoodName(FoodName);
                        od.setNumber(Number);
                        od.setFoodId(FoodId);
                        od.setSellPrice(FoodSp);
                        od.setState(NO_PLACE_AN_ORDER);
                        od.setAppend(NO_PLACE_AN_ORDER);

//                                olist.add(od);

                        ORDERFLAG = true;
                        Intent intent = new Intent();
                        Bundle mBundle = new Bundle();

                        mBundle.putSerializable("order_list", od);
                        intent.putExtras(mBundle);
                        //设置返回数据
                        OrderActivity.this.setResult(RESULET_CODE, intent);

                        CommonUtil.Exit(OrderActivity.this);

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
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ed = Integer.parseInt(edt.getText().toString());

                if (ed <= 1) {

                    return;
                }
                String b = String.valueOf(ed - 1);
                edt.setText(b);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ed = Integer.parseInt(edt.getText().toString());

                if (ed > 99) {

                    return;
                }
                String b = String.valueOf(ed + 1);
                edt.setText(b);


            }
        });


    }

    private void init() {

        olist = new ArrayList<Order>();

        Out_Of_Print = (ImageView) findViewById(R.id.Out_Of_Print);

        edt = (TextView) findViewById(R.id.edt);
        order_food_name = (TextView) findViewById(R.id.order_food_name);
        order_food_price = (TextView) findViewById(R.id.order_food_price);
        minus = (TextView) findViewById(R.id.minus);
        add = (TextView) findViewById(R.id.add);
        cancel = (TextView) findViewById(R.id.cancel);
        submit = (TextView) findViewById(R.id.submit);
        shouwan = (TextView) findViewById(R.id.shouwan);
        jiage = (TextView) findViewById(R.id.jiage);

        close = (TextView) findViewById(R.id.close);

        add_layout = (RelativeLayout) findViewById(R.id.add_layout);

    }

    private class FoodStateAsyck extends AsyncTask {
        String result;

        @Override
        protected Object doInBackground(Object[] params) {

            String Name = (String) params[0];
            String Id = (String) params[1];

            Log.i(TAG, "AsyncTask Send Name= " + Name + "AsyncTask Send Id =" + Id);


            String menukey = VERIFY_KEY;

            StringBuilder sb = new StringBuilder(MHTTP + FOOD_STATE_HHTP);

            sb.append("?");

            try {

                sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");
                sb.append("FoodName=" + URLEncoder.encode(Name.toString(), "UTF-8") + "&");
                sb.append("FoodId=" + URLEncoder.encode(Id.toString(), "UTF-8") + "&");

                result = HttpURlConnection.getHttpURlConnection(sb);
//                Log.i(TAG, "1111111菜单状态返回结果为  " + result);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Object result) {

//            String result = (String) o;

            if (result == null) {

                Toast.makeText(OrderActivity.this, "无法与服务器连接", Toast.LENGTH_SHORT).show();

                ShowAndHide();
                closeClick();
                Log.i(TAG, "无法与服务器连接  result ===" + result);

//                CommonUtil.Exit(OrderActivity.this);
                return;
            }

            Log.i(TAG, "222222222菜单状态返回结果为  " + result);

            if (result.equals("true")) {

                order_food_name.setText(FoodName);
                order_food_price.setText(FoodSp);
                edt.setText("1");
                click();
                closeClick();
                Log.i(TAG, "成功查询到食物Status为1,进入确认添加菜式状态");
                return;
            }
            if (result.equals("false")) {

                ShowAndHide();
                closeClick();
                Log.i(TAG, "food_State为3或者0,隐藏菜式下单信息");
                return;
            }
            if (result.equals("error")) {

                Log.i(TAG, "发送的FoodName和FoodId无法查询到Status");

                ShowAndHide();
                closeClick();
//                CommonUtil.Exit(OrderActivity.this);
                return;
            }

        }
    }

    /**
     * 将某些控件隐藏和显示;
     */
    private void ShowAndHide() {

        jiage.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        add_layout.setVisibility(View.GONE);
        close.setVisibility(View.VISIBLE);
        Out_Of_Print.setVisibility(View.VISIBLE);

        shouwan.setVisibility(View.VISIBLE);


    }
}
