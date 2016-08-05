package com.menusystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.menusystem.R;
import com.menusystem.bean.Data;
import com.menusystem.bean.MenuType;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.HttpURlConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.menusystem.bean.Verify.FOOD_MENU_HHTP;
import static com.menusystem.bean.Verify.FOOD_TYPE_HHTP;
import static com.menusystem.bean.Verify.HTTP;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.bean.Verify.VERIFY_KEY;

/**
 * Created by ${Kikis} on 2016-07-22.
 */
public class Welcome_Activity extends Activity {
    private List<MenuType> typeList;
    private List<Data> dataList;

    public static final String TAG= "Welcome_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deploy();
        setContentView(R.layout.welcome_activity);
        networking();

    }

    private void networking() {


        NetworkInfo info  = CommonUtil.getNetworkInfo(Welcome_Activity.this);

        if(info!=null&&info.isAvailable()){

            new Asynctask().execute();

            }else{

            Toast.makeText(Welcome_Activity.this,"没有检测到网络连接...",Toast.LENGTH_SHORT).show();

            CommonUtil.entirelyexit(this);

        }

    }

    private void deploy() {

        CommonUtil.setNoTitleBar(Welcome_Activity.this);
        CommonUtil.setFullScreen(Welcome_Activity.this);

    }


    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);

                    Intent intent = new Intent(Welcome_Activity.this,MainActivity.class);

                    intent.putExtra("menu_type", (Serializable) typeList);
                    intent.putExtra("food_menu", (Serializable) dataList);

                    startActivity(intent);

                    CommonUtil.Exit(Welcome_Activity.this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private class Asynctask extends AsyncTask {

        String TypeResult="";
        String FoodMenuResult="";

        @Override
        protected Object doInBackground(Object[] objects) {

            String menukey = VERIFY_KEY;

            StringBuilder sb = new StringBuilder(HTTP+FOOD_TYPE_HHTP);

            StringBuilder sb2 = new StringBuilder(MHTTP+FOOD_MENU_HHTP);

            sb2.append("?");


            sb.append("?");
            try {

                sb.append("menukey="+ URLEncoder.encode(menukey.toString(),"UTF-8")+"&");

                sb2.append("menukey="+ URLEncoder.encode(menukey.toString(),"UTF-8")+"&");

                TypeResult = HttpURlConnection.getHttpURlConnection(sb);

                FoodMenuResult = HttpURlConnection.getHttpURlConnection(sb2);



                JSONArray jsonar2 = new JSONArray(FoodMenuResult);

                dataList = new ArrayList<Data>();

                for(int i=0; i<jsonar2.length();i++){

                    JSONObject jsonob2 = jsonar2.getJSONObject(i);

                    Data data = new Data();

                    data.setSystemID(jsonob2.getString("SystemID"));
                    data.setFoodID(jsonob2.getString("FoodID"));
                    data.setFoodTypeID(jsonob2.getString("FoodTypeID"));
                    data.setFoodName(jsonob2.getString("FoodName"));
                    data.setSellPrice(jsonob2.getString("SellPrice"));
                    data.setFoodPicAddress(jsonob2.getString("FoodPicAddress"));

                    dataList.add(data);

                }


                JSONArray jsonar  = new JSONArray(TypeResult);

                typeList = new ArrayList<MenuType>();

                for(int i=0; i < jsonar.length();i++){

                    JSONObject jsonob = jsonar.getJSONObject(i);

                    MenuType type = new MenuType();

                    type.setSystemID(jsonob.getString("SystemID"));
                    type.setFoodTypeID(jsonob.getString("FoodTypeID"));
                    type.setFoodTypeName(jsonob.getString("FoodTypeName"));
                    type.setFoodTypeArea(jsonob.getString("FoodTypeArea"));

                    typeList.add(type);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            if(typeList!=null&&typeList.size()>0&&dataList!=null&&dataList.size()>0){


                startThread();

            }else{

                Log.i(TAG,MY_LOGCAT_TAG+"无法从服务端获取数据");

                Toast.makeText(Welcome_Activity.this,"无法从服务端获取数据...",Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {

                            Thread.sleep(2000);

                            CommonUtil.Exit(Welcome_Activity.this);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }





        }
    }

}
