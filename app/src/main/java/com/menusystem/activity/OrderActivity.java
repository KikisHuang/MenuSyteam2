package com.menusystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.menusystem.R;
import com.menusystem.bean.Order;
import com.menusystem.util.AlertDialogUtils;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.HttpURlConnection;
import com.menusystem.view.FlowRadioGroup;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private TextView cancel, submit, order_food_name, order_food_price, minus, add, edt, shouwan, jiage, close, marked;
    private FlowRadioGroup radio_group;
    private ImageView Out_Of_Print;
    private String FoodName, FoodId, FoodSp;
    private RelativeLayout add_layout,button_layout;
    public static final String TAG = "OrderActivity";
    public List<Order> olist;
    private List<String> dlist;
    private int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        addActivity(this);
        init();
        receiver();
        setMessage();
    }


    /**
     * 设置菜品选项栏;
     *
     * @param list
     */
    private void setRadioButton(List<String> list) {

        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(120, 65);

        params_rb.setMargins(0, 4, 15, 0);

        for (int i = 0; i < list.size(); i++) {

            if (list.size() >= 4) {

                ViewGroup.LayoutParams lp;
                lp = radio_group.getLayoutParams();

                lp.width = 700;
                params_rb.setMargins(40, 4, 0, 0);
                radio_group.setLayoutParams(lp);

            }

            RadioButton tempButton = new RadioButton(this);
            tempButton.setBackgroundResource(R.drawable.radio_selector);   // 设置RadioButton的背景图片
            tempButton.setButtonDrawable(R.color.transparent);           // 设置按钮的样式
            tempButton.setPadding(10, 0, 0, 0);                 // 设置文字距离按钮四周的距离
            tempButton.setText(list.get(i));
            tempButton.setTextColor(Color.BLACK);
            tempButton.setGravity(Gravity.CENTER);
            tempButton.setTextSize(13);
            final int finalI = i;
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     ID = finalI;
                    Log.i(TAG, " button id = " + ID);
                }
            });
            radio_group.addView(tempButton, params_rb);

            if (i == 0) {

                tempButton.setChecked(true);
                Log.i(TAG, " button id = " + ID);
            } else {
                tempButton.setChecked(false);
            }
        }
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
                        int Number = 0;
                        Number = Integer.parseInt(edt.getText().toString());

                        Order od = new Order();

                        od.setFoodName(FoodName);
                        od.setNumber(Number);
                        od.setFoodId(FoodId);
                        od.setSellPrice(FoodSp);
                        od.setState(NO_PLACE_AN_ORDER);
                        od.setAppend(NO_PLACE_AN_ORDER);
                        if(radio_group.getVisibility()==View.GONE||radio_group.getVisibility()==View.INVISIBLE){
                            od.setDetailName("默认");
                        }else{
                            od.setDetailName(dlist.get(ID));
                        }

//                                olist.add(od);

                        ORDERFLAG = true;
                        Intent intent = new Intent();
                        Bundle mBundle = new Bundle();

                        mBundle.putSerializable("addorder", od);
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
        marked = (TextView) findViewById(R.id.marked);
        marked.setVisibility(View.GONE);

        radio_group = (FlowRadioGroup) findViewById(R.id.radio_group);
        //初始化隐藏菜品特定选项布局;
        radio_group.setVisibility(View.GONE);

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
                Log.i(TAG, "菜品实时状态返回结果为  " + result);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Object result) {


            if (result == null) {

                AlertDialogUtils.getOnlyDialog(OrderActivity.this, "无法与服务器连接");

                ShowAndHide();
                closeClick();
                Log.i(TAG, "无法与服务器连接  result ===" + result);

            }

            if (result.equals("true")) {

                order_food_name.setText(FoodName);
                order_food_price.setText(FoodSp);
                edt.setText("1");

                ShowView(1);
                Log.i(TAG, "成功查询到食物Status为1,进入确认添加菜式状态");
                closeClick();
                click();
            }

            if (!result.equals("true") && !result.equals("false") && !result.equals("error") && !result.equals("")) {

                try {
                    JSONArray detail = new JSONArray((String)result);

                    dlist = new ArrayList<>();

                    dlist.add("默认");
                    for (int i = 0; i < detail.length(); i++) {

                        JSONObject object = detail.getJSONObject(i);


                        dlist.add(object.getString("DetailName"));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ShowView(2);
                setRadioButton(dlist);
                closeClick();
                click();
            }

            if (result.equals("false")) {

                ShowAndHide();
                closeClick();
                Log.i(TAG, "food_State为3或者0,隐藏菜式下单信息");
            }
            if (result.equals("error")) {

                Log.i(TAG, "发送的FoodName和FoodId无法查询到Status");

                ShowAndHide();
                closeClick();
//                CommonUtil.Exit(OrderActivity.this);
            }


        }
        //动态设置按钮布局;
        private void ShowView(int flge) {

            if(flge==1){

                button_layout = (RelativeLayout) findViewById(R.id.button_layout);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(150, 110);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(150, 110);

                lp.addRule(RelativeLayout.LEFT_OF,R.id.zhongxian);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                lp1.addRule(RelativeLayout.RIGHT_OF,R.id.zhongxian);
                lp1.addRule(RelativeLayout.CENTER_IN_PARENT);

                lp.setMargins(0,0,30,0);
                lp1.setMargins(30,0,0,0);

                submit.setLayoutParams(lp);
                cancel.setLayoutParams(lp1);
//                button_layout.addView(submit,lp);
//                button_layout.addView(cancel,lp1);
            }
            if(flge==2){

                radio_group.setVisibility(View.VISIBLE);
                marked.setVisibility(View.VISIBLE);
                order_food_name.setText(FoodName);
                order_food_price.setText(FoodSp);
                edt.setText("1");

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
        marked.setVisibility(View.INVISIBLE);

        shouwan.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

    }
}
