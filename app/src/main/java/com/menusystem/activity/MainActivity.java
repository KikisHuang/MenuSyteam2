package com.menusystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.menusystem.R;
import com.menusystem.adapter.FoodTypeAdapter;
import com.menusystem.adapter.OrderAdapter;
import com.menusystem.adapter.TravelAdapter;
import com.menusystem.asynctask.OrderQueryAsynctask;
import com.menusystem.asynctask.PlaceOrderAsyncTask;
import com.menusystem.bean.Append;
import com.menusystem.bean.Data;
import com.menusystem.bean.Four;
import com.menusystem.bean.MenuType;
import com.menusystem.bean.Order;
import com.menusystem.bean.OrderStatus;
import com.menusystem.bean.PlaceOrder;
import com.menusystem.sliderview.SliderListView;
import com.menusystem.util.AlertDialogUtils;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.LruCacheUtils;
import com.menusystem.util.Partition;
import com.menusystem.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import static com.menusystem.adapter.TravelAdapter.BitmapRecycle;
import static com.menusystem.bean.Verify.ADD_WATER;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.bean.Verify.NO_PLACE_AN_ORDER;
import static com.menusystem.bean.Verify.OFF_THE_STOCKS;
import static com.menusystem.bean.Verify.ORDERFLAG;
import static com.menusystem.bean.Verify.ORDERSUCCEED;
import static com.menusystem.bean.Verify.PAY_THE_BILL;
import static com.menusystem.bean.Verify.PLACE_AN_ORDER;
import static com.menusystem.bean.Verify.REMINDER;
import static com.menusystem.bean.Verify.TABLE_NUMBER;
import static com.menusystem.bean.Verify.THREADCONTROL;
import static com.menusystem.database.DatabaseUtils.CloseDb;
import static com.menusystem.util.AddMessageUtils.NewMeassge;
import static com.menusystem.util.AlertDialogUtils.getCommonDialog;
import static com.menusystem.util.AlertDialogUtils.getOnlyDialog;
import static com.menusystem.util.AlertDialogUtils.getPayTheBillDiaglog;
import static com.menusystem.util.CommonUtil.addActivity;
import static com.menusystem.util.OrderoverlayUtil.OverlayOrderjudge;
import static com.menusystem.util.SharedPreference.setSharedPreference;
import static com.menusystem.util.TableNumChange.getTableAlertDialog;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static TextView table_number;
    private TextView zhuohao;
    private static TextView all_prices;
    private int Flag;
    private long mkeyTime;
    private ListView leftmenu;
    private static SliderListView right_listView;
    private static OrderAdapter oadapter;
    private Button xiadan, mai, cui, add_water;
    public List<MenuType> list;
    public List<Data> dlist;
    public List<Four> alist;
    static List<Order> olist;
    private static Handler mHandler;
    private LinearLayout MainRl;
    private FlipViewController flipView;
    private TravelAdapter tadapter;
    private FoodTypeAdapter adapter;
    private static String CSID = "";
    private static String TableNumber = "";
    private static String SystemId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wei_test);
        addActivity(this);
        Log.i(TAG, MY_LOGCAT_TAG + "onCreate");
        init();
        initFlag();
        OrderDataBase();
        MyHandler();
        GainTableNumber();
        receive();
        Dataswitch();
        Flipinit();
        Layoutinit();
        LeftMunu();
        MenuSelect();
        TableNumber();
        PlaceOrder();
        Click();
        GainAllPrices();
    }
    /**
     * 初始化标识符
     */
    private void initFlag() {
        //成功下单标识符
        ORDERSUCCEED = false;
        //查询线程标识符
        THREADCONTROL = false;
        //ActivityResult标识符
        ORDERFLAG = false;
        //买单标识
        PAY_THE_BILL = false;

    }

    /**
     * 计算总价
     */
    public static void GainAllPrices() {

        int All_Prices = 0;

        if (olist.size() >= 0) {
            for (int i = 0; i < olist.size(); i++) {
                double a = Double.parseDouble(olist.get(i).getSellPrice());

                int b = olist.get(i).getNumber();

                double c = a * b;

                All_Prices += c;
            }
            all_prices.setText(String.valueOf(All_Prices));
        } else {
            return;
        }
    }

    public static void SetCsid(String id,Activity ac){

        CSID = id;
        setSharedPreference(ac, "CSID",id);
    }
    /**
     * 判断是否第一次生成订单id;
     */
    private void OrderDataBase() {

        CSID = SharedPreference.getSahrePreference(this, "CSID");


        if (!CSID.equals("")) {


            new OrderQueryAsynctask(MainActivity.this).execute(CSID);

            ORDERSUCCEED = true;
            Log.i(TAG, "获得上次未买单的订单编号,继续使用,同时从数据库中获得菜单信息-----CSID ===" + CSID);


        } else {

            oadapter = new OrderAdapter(MainActivity.this, olist);

            right_listView.setAdapter(oadapter);

            Log.i(TAG, "没有订单号-----CSID ===" + CSID);
        }

    }

    public static void AddOrder(Order od,Activity context){

        olist.add(od);

        oadapter = new OrderAdapter(context, olist);

        right_listView.setAdapter(oadapter);
    }


    /**
     * 更新右侧菜单列表UI;
     */
    public static void MyHandler() {

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                //判断
                switch (msg.what) {
                    case 1:
                        oadapter.notifyDataSetChanged();
                        Log.i(TAG, "handleMessage更新UI");
                        break;
                }
            }
        };

    }

    /**
     * 加水、催单、买单点击事件;
     */
    private void Click() {

        add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Olistjudge(ADD_WATER);
            }
        });

        cui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Olistjudge(REMINDER);
            }
        });

        mai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayTheBillDiaglog(MainActivity.this,CSID,SystemId,TableNumber,olist);

            }
        });

    }

    /**
     * 是否下单判断;
     * true:消息发送成功;
     * false:提示;
     */
    private void Olistjudge(int num){

        if(olist.size()>0&&olist.get(0).getState()!=NO_PLACE_AN_ORDER){

            getCommonDialog(MainActivity.this,num);

        }else{

            getOnlyDialog(MainActivity.this,"下单后才能执行此类操作。");
            Log.i(TAG, "菜单没有下单状态的菜品,打印 olist.size="+olist.size());
            return;
        }
    }

    /**
     * 起线程实时查询状态;

    public static void StartThread() {

        if (!THREADCONTROL) {

            if (ORDERSUCCEED) {
                Log.i(TAG, "ORDERSUCCEED为true,启动查询");

                PatrolThread Pt = new PatrolThread(CSID, SystemId);

                Pt.start();

            } else {

                Log.i(TAG, "ORDERSUCCEED为false,不启动查询");

                return;
            }

        } else {
            Log.i(TAG, "有线程查询中...不启动查询");

            return;
        }


    }
     */



    /**
     * 修改菜式状态码方法1;
     *
     * @param
     */
    public static void ChangeOrderStatus(List<Append> adlist) {

        for (int i = 0; i < adlist.size(); i++) {

            String PId = adlist.get(i).getFoodID();
            int ANum = adlist.get(i).getFoodNumber();
            int Append = adlist.get(i).getAppend();
            int FoodStatus = adlist.get(i).getFoodStatus();

            for (int s = 0; s < olist.size(); s++) {

                String OId = olist.get(s).getFoodId();
                int ONum = olist.get(s).getNumber();
                int OAppend = olist.get(s).getAppend();
                int OFoodStatus = olist.get(s).getState();

                if (PId.equals(OId) && ANum == ONum && OAppend == NO_PLACE_AN_ORDER && OFoodStatus == NO_PLACE_AN_ORDER) {

                    olist.get(s).setState(FoodStatus);
                    olist.get(s).setAppend(Append);

                    Log.i(TAG, "修改已下单状态码..." + olist.get(s));
                    break;
                }
            }

        }

        oadapter.notifyDataSetChanged();
    }

    /**
     * 修改菜式状态码方法2;
     */
    public static void ChangeOrderStatusTwo(List<OrderStatus> slist) {

        for (int i = 0; i < slist.size(); i++) {

            String PId = slist.get(i).getFoodID();
            int PNum = slist.get(i).getFoodNumber();
            int Append = slist.get(i).getAppend();

            for (int s = 0; s < olist.size(); s++) {

                int Ostate = olist.get(s).getState();
                int Onum = olist.get(s).getNumber();
                int OAppend = olist.get(s).getAppend();
                String OId = olist.get(s).getFoodId();

                if (PId.equals(OId) && Ostate == PLACE_AN_ORDER && PNum == Onum && OAppend == Append) {

                    olist.get(s).setState(OFF_THE_STOCKS);

                    //更新UI方法  1
                    Message message = new Message();
                    message.what = 1;

                    mHandler.sendMessage(message);

                    Log.i(TAG, "循环线程修改已上菜状态码..." + olist.get(s));

                }
            }

        }
        int k = 0;
        for (int j = 0; j < olist.size(); j++) {

            int Ostate = olist.get(j).getState();

            if (Ostate == OFF_THE_STOCKS) {

                ++k;
            }

            if (k == olist.size()) {

                ORDERSUCCEED = false;
                THREADCONTROL = false;

                Log.i(TAG, "查询出菜品已全部上齐,关闭查询线程");

            }

        }

    }

    /**
     * 获得SharedPreference中的桌号;
     */
    private void GainTableNumber() {
        String TNumber = SharedPreference.getSahrePreference(this, TABLE_NUMBER);

        if (!TNumber.equals("")) {

            Log.i(TAG, "sp中存在数据,取出桌号 = " + TNumber + "赋予");
            table_number.setText(TNumber);
            TableNumber = TNumber;
            return;
        } else {
            Log.i(TAG, "sp中不存在数据return");

            return;
        }
    }

    /**
     * 下单;
     */
    private void PlaceOrder() {

        xiadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (olist.size() > 0) {

                    getAlertDialog();

                } else {

                    getOnlyDialog(MainActivity.this,"未接受到菜单栏的菜式,请点菜后再下单。");
                    return;
                }

            }
        });
    }

    /**
     * 下单Dialog;
     */
    public void getAlertDialog() {

        new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("您确定要下单了吗?")//提示框标题
                .setPositiveButton("确定",//提示框的两个按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                CreateSystemId();
                                return;
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        return;
                    }
                }).create().show();

    }

    /**
     * 创建订单;
     */
    private void CreateOrder() {

        String Date = CommonUtil.getDate();
        String Time = CommonUtil.getTime();

        Log.i(TAG, "获得的开始日期==== " + Date + "----开始时间====" + Time);

        List<PlaceOrder> plist = new ArrayList<>();

        for (int i = 0; i < olist.size(); i++) {

            if (olist.get(i).getState() == NO_PLACE_AN_ORDER) {

                PlaceOrder Po = new PlaceOrder();

                Po.setSystemID(SystemId);
                Po.setCSID(CSID);
                Po.setDeskID(TableNumber);
                Po.setOrderStatus(0);//订单状态,0开始9结束;
                Po.setOrderCoupon(1);//折扣,默认1;
                Po.setStartDate(Date);
                Po.setStartTime(Time);
                Po.setEndDate(Date);
                Po.setEndTime(Time);
                Po.setSellPrice(olist.get(i).getSellPrice());
                Po.setFoodStatus(PLACE_AN_ORDER);
                Po.setFoodId(olist.get(i).getFoodId());
                Po.setFoodNumber(olist.get(i).getNumber());

                plist.add(Po);
            }
        }

        if (plist.size() > 0) {

            new PlaceOrderAsyncTask(MainActivity.this).execute(plist);

        } else {

            Toast.makeText(this, "没有未下单状态的菜品...", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "没有未下单状态的菜品...");
        }

    }

    /**判断是否二次下单;

     */
    private void CreateSystemId() {


        if (!CSID.equals("")) {

            Log.i(TAG, "CSID不为空===" + CSID);

            CreateOrder();

            return;
        } else {

            String Time = CommonUtil.getDateAndTime();
            Log.i(TAG, "获得的系统当前时间为  =   " + Time);

            TableNumber = table_number.getText().toString();

            if (!TableNumber.equals("0000") && !TableNumber.equals("")) {

                CSID = Time + TableNumber;

                Log.i(TAG, "获得的系统id为  =   " + SystemId);

                Log.i(TAG, "组合成的订单ID为" + CSID);

                CreateOrder();

                return;
            } else {

//                Toast.makeText(MainActivity.this, "桌号有误,请更换桌号后再下单!", Toast.LENGTH_SHORT).show();
                getOnlyDialog(MainActivity.this,"桌号有误,请更换桌号后再下单!");
                Log.i(TAG, "桌号有误,请联系服务员更换桌号后再下单!");
                return;

            }
        }

    }

    private void TableNumber() {

        zhuohao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTableAlertDialog(MainActivity.this,olist);
            }
        });
        table_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTableAlertDialog(MainActivity.this, olist);
            }
        });
    }


    /**
     * 桌号修改成功操作..
     */
    public static void SetTableNum(Activity context, String value) {

        //添加到Sp中
        setSharedPreference(context, TABLE_NUMBER, value);
        //给全局变量赋值
        TableNumber = value;
        table_number.setText(value);
//        Toast.makeText(context, "桌号修改成功...", Toast.LENGTH_SHORT).show();

        AlertDialogUtils.getOnlyDialog(context,"桌号修改成功...");

    }


    /**
     * FlipView左右联动显示菜单不同状态
     */
    private void MenuSelect() {

        flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
            @Override
            public void onViewFlipped(View view, int position) {

                for (int i = 0; i < leftmenu.getChildCount(); i++) {

                    String b = list.get(i).getFoodTypeID();
                    String a1 = alist.get(position).getFoodTypeID1();
                    String a2 = alist.get(position).getFoodTypeID2();
                    String a3 = alist.get(position).getFoodTypeID3();
                    String a4 = alist.get(position).getFoodTypeID4();

                    if (a1.equals(b)||a2.equals(b)||a3.equals(b)||a4.equals(b)) {

                        leftmenu.getChildAt(i).setBackgroundColor(MainActivity.this.getResources().getColor(R.color.black5));

                        clear(i);
                        Log.i(TAG, " Last != list.get(i).getFoodTypeID() 进行setSelector");
                        break;

                    } else {

                        leftmenu.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }

                }
            }
        });

    }

    /**
     * 清除ChildCount不一样的Background
     */
    private void clear(int ChildCount) {

        if (Flag != ChildCount) {

            leftmenu.getChildAt(Flag).setBackgroundColor(Color.TRANSPARENT);

            Flag = ChildCount;
        }
    }

    /**
     * 左侧菜单事件
     */
    private void LeftMunu() {

        leftmenu.setItemsCanFocus(true);

        adapter = new FoodTypeAdapter(MainActivity.this, list);

        leftmenu.setAdapter(adapter);


        leftmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {

                /**
                 *LeftmenuListView点击事件跳转FlipView;左右联动显示菜单不同状态
                 */
                if (alist.size() > 0) {


                    for (int j = 0; j < leftmenu.getChildCount(); j++) {

                        if (position == j) {

                            leftmenu.getChildAt(j).setBackgroundColor(MainActivity.this.getResources().getColor(R.color.black5));

                            clear(j);
                            break;

                        }
                    }

                    for (int i = 0; i < alist.size(); i++) {

                        if (list.get(position).getFoodTypeID().equals(alist.get(i).getFoodTypeID1()) || list.get(position).getFoodTypeID().equals(alist.get(i).getFoodTypeID2()) || list.get(position).getFoodTypeID().equals(alist.get(i).getFoodTypeID3())) {

                            flipView.setSelection(i);
                            Log.i(TAG, MY_LOGCAT_TAG + "getFoodTypeID == " + list.get(position).getFoodTypeID());

                            break;
                        }
                    }
                }
//                Log.i(TAG, MY_LOGCAT_TAG + "getFoodTypeID == " + list.get(position).getFoodTypeID());
//
//                for (int i = 0; i < leftmenu.getChildCount(); i++) {
//
//                    if (position == i) {
//
//                        leftmenu.getChildAt(i).setBackgroundColor(MainActivity.this.getResources().getColor(R.color.black5));
//
//                        clear(i);
//                        break;
//
//                    }
//
//                }
            }
        });


    }

    /**
     * 数据分组
     */
    private void Dataswitch() {

        try {
            alist = Partition.GetDataSwitch(dlist);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * 接收到Welcome_Activity传来的MenuType和Data;
     */
    private void receive() {

        list = (List<MenuType>) getIntent().getSerializableExtra("menu_type");


        dlist = (List<Data>) getIntent().getSerializableExtra("food_menu");

    }

    /**
     * RelativeLayout布局设置防止布局wrap_content错乱
     */
    private void Layoutinit() {


        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        flipView.setLayoutParams(ll);

        MainRl.addView(flipView);

    }

    /**
     * flipView显示设置、setAdapter
     */
    private void Flipinit() {

        flipView = new FlipViewController(MainActivity.this, FlipViewController.HORIZONTAL);

        tadapter = new TravelAdapter(MainActivity.this, alist, dlist);

        flipView.setAdapter(tadapter);

    }

    public static void Send(int addWater, Context context) {

        NewMeassge(SystemId, context, addWater, CSID, TableNumber);

    }

    private void init() {
        //获得系统Id;
        SystemId = SharedPreference.getSahrePreference(MainActivity.this, "SystemId");

        olist = new ArrayList<>();

        cui = (Button) findViewById(R.id.cui);
        xiadan = (Button) findViewById(R.id.xiadan);
        mai = (Button) findViewById(R.id.mai);
        add_water = (Button) findViewById(R.id.add_water);

        zhuohao = (TextView) findViewById(R.id.zhuohao);
        all_prices = (TextView) findViewById(R.id.all_prices);
        table_number = (TextView) findViewById(R.id.table_number);

        leftmenu = (ListView) findViewById(R.id.leftmenu);
        right_listView = (SliderListView) findViewById(R.id.right_listView);

        MainRl = (LinearLayout) findViewById(R.id.MainRl);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /**二次点击退出*/
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();

            } else {

                onDestroy();
                CommonUtil.entirelyexit();

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ORDERFLAG) {

            Order od;

            od = (Order) data.getSerializableExtra("order_list");

            olist = OverlayOrderjudge(olist,od);

            Log.i(TAG, "My onActivityResult " + od.getFoodName());

            oadapter.notifyDataSetChanged();
            GainAllPrices();

            ORDERFLAG = false;

        } else {

            return;
        }

    }

    /**
     * 判断是否买单;
     * true:清除sql、sp中的数据;
     * false:往sql、sp中写入数据;
     */
    public static  void DataJudge(Activity context) {
//
        if (!PAY_THE_BILL) {
            setSharedPreference(context, "CSID", CSID);
//
//            DeleteTb();
//            Log.i(TAG, MY_LOGCAT_TAG + "清空SQlite数据库信息...");
//            if (olist.size() > 0) {
//                for (int i = 0; i < olist.size(); i++) {
//                    String FoodName = olist.get(i).getFoodName();
//                    String SellPrice = olist.get(i).getSellPrice();
//                    String FoodId = olist.get(i).getFoodId();
//                    int Append = olist.get(i).getAppend();
//                    int Number = olist.get(i).getNumber();
//                    int State = olist.get(i).getState();
//
//                    InsertTb(FoodName, SellPrice, FoodId, Append, Number, State);
//                    Log.i(TAG, MY_LOGCAT_TAG + "插入SQlite数据库信息...");
//                }
//            }
//
        } else {

//            DeleteTb();
            SharedPreference.removeSharedPreference(context, "CSID");
            Log.i(TAG, MY_LOGCAT_TAG + "清空SharedPreference信息...");
        }
    }


    /**
     * 生命周期logcat
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, MY_LOGCAT_TAG + "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, MY_LOGCAT_TAG + "onPause");
        RemoveCache();
        Log.i(TAG, "onPause方法启动清理应用缓存，防止OOM");
    }


    @Override
    protected void onStop() {
        super.onStop();
        RemoveCache();
        Log.i(TAG, MY_LOGCAT_TAG + "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, MY_LOGCAT_TAG + "onResume");
        RemoveCache();
        Log.i(TAG, "onResume方法启动清理应用缓存，防止OOM");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, MY_LOGCAT_TAG + "onRestart");
    }

    @Override
    protected void onDestroy() {
        BitmapRecycle();
        CloseDb();
        RemoveCache();
        Log.i(TAG, "onDestroy方法启动清理应用缓存，防止OOM");
        super.onDestroy();
        Log.i(TAG, MY_LOGCAT_TAG + "onDestroy");

    }

    /**
     * 清除缓存方法;
     */
    private void RemoveCache() {

        LruCacheUtils lcu = new LruCacheUtils();

        lcu.clearCache();

        System.gc();
        int Chace1 = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);
        Log.i(TAG, "当前缓存为:" + Chace1 + "-----------");
    }

}
