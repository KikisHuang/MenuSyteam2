package com.menusystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.menusystem.R;
import com.menusystem.bean.Data;
import com.menusystem.bean.MenuType;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.DownloadService;
import com.menusystem.util.HttpURlConnection;
import com.menusystem.util.SharedPreference;
import com.menusystem.util.fileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.menusystem.bean.Verify.FOOD_MENU_HHTP;
import static com.menusystem.bean.Verify.FOOD_TYPE_HHTP;
import static com.menusystem.bean.Verify.MHTTP;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.bean.Verify.VERIFY_KEY;
import static com.menusystem.bean.Verify.VERSION_QUERY;
import static com.menusystem.util.AlertDialogUtils.getVersionDialog;
import static com.menusystem.util.CommonUtil.addActivity;
import static com.menusystem.util.CommonUtil.getVersion;
import static com.menusystem.util.DownloadService.CACHE_FILENAME_PREFIX;
import static com.menusystem.util.LogUtils.PrintError;

/**
 * Created by ${Kikis} on 2016-07-22.
 */
public class Welcome_Activity extends Activity {

    public static List<MenuType> typeList;
    public static List<Data> dataList;
    public static List<String> listUrl;
    public static String path = "";
    private static final String TAG = "Welcome_Activity";
    private ImageView img;
    private static String Version = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
        CreateDataBase();
        deploy();
        setContentView(R.layout.welcome_activity);
        setImage();
        networking();

    }

    private void setImage() {
        img = (ImageView) findViewById(R.id.WelCome_img);
        img.setBackgroundResource(R.drawable.welcome_img);
    }

    private void CreateDataBase() {
        /**
         * Sqlite数据库创建,存储临时菜单信息
         */
//        DatabaseUtils Du = new DatabaseUtils(Welcome_Activity.this);
//
//        CreateDatabase("Cristiano");
    }

    private void networking() {


        NetworkInfo info = CommonUtil.getNetworkInfo(Welcome_Activity.this);

        if (info != null && info.isAvailable()) {

            new Asynctask(Welcome_Activity.this).execute();

        } else {

            Toast.makeText(Welcome_Activity.this, "没有检测到网络连接...", Toast.LENGTH_SHORT).show();

            exit(Welcome_Activity.this);

        }

    }

    private void deploy() {

        CommonUtil.setNoTitleBar(Welcome_Activity.this);
        CommonUtil.setFullScreen(Welcome_Activity.this);

        /**
         * 获取当前屏幕密度
         */
        float density = Welcome_Activity.this.getResources().getDisplayMetrics().density;

        Log.i(TAG,"输出当前设备屏幕密度 =========="+density);
    }

    private static void start(Activity ac) {

            skipActivity(ac);
    }


    public static void skipActivity(Activity ac){

        Intent intent = new Intent(ac, MainActivity.class);

        intent.putExtra("menu_type", (Serializable) typeList);
        intent.putExtra("food_menu", (Serializable) dataList);

        ac.startActivity(intent);

        CommonUtil.Exit(ac);

    }

    public static class Asynctask extends AsyncTask {

        String TypeResult = "";
        String FoodMenuResult = "";
        Activity ac;

        public Asynctask(Welcome_Activity welcome_activity) {
            this.ac = welcome_activity;
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            String menukey = VERIFY_KEY;

            StringBuilder sb = new StringBuilder(MHTTP + FOOD_TYPE_HHTP);

            StringBuilder sb2 = new StringBuilder(MHTTP + FOOD_MENU_HHTP);

            StringBuilder sb3 = new StringBuilder(MHTTP + VERSION_QUERY);

            sb.append("?");
            sb2.append("?");
            sb3.append("?");
            try {

                sb.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

                sb2.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");

                sb3.append("menukey=" + URLEncoder.encode(menukey.toString(), "UTF-8") + "&");


                TypeResult = HttpURlConnection.getHttpURlConnection(sb);

                FoodMenuResult = HttpURlConnection.getHttpURlConnection(sb2);

                Version = HttpURlConnection.getHttpURlConnection(sb3);

                JSONArray jsonar2 = new JSONArray(FoodMenuResult);

                dataList = new ArrayList<Data>();

                for (int i = 0; i < jsonar2.length(); i++) {

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

                JSONArray jsonar = new JSONArray(TypeResult);

                typeList = new ArrayList<MenuType>();

                for (int i = 0; i < jsonar.length(); i++) {

                    JSONObject jsonob = jsonar.getJSONObject(i);

                    MenuType type = new MenuType();

                    type.setSystemID(jsonob.getString("SystemID"));
                    type.setFoodTypeID(jsonob.getString("FoodTypeID"));
                    type.setFoodTypeName(jsonob.getString("FoodTypeName"));

                    typeList.add(type);

                }

            } catch (Exception e) {
                PrintError(TAG,e);
                return null;
            }

            return null;
        }


        @Override
        protected void onPostExecute(Object o) {

            if(!Version.equals(getVersion(ac).trim())&&Version.length()>0&&!Version.equals("NO_File")) {

                getVersionDialog(ac, Version);

                Log.i(TAG,"有新版本....Version = "+Version);
                Log.i(TAG,"当前的版本为....Version = "+getVersion(ac).trim());
            }else{
                Operation(ac);
                Log.i(TAG,"当前的版本为....Version = "+getVersion(ac).trim());
            }
        }
        public static void Operation(Activity ac){

            //判断是否获得数据
            if (typeList != null && typeList.size() > 0 && dataList != null && dataList.size() > 0) {

                //Sp存储一下系统id
                SharedPreference.setSharedPreference(ac, "SystemId", typeList.get(1).getSystemID());

                Log.i(TAG, "开始下载图片!!!!!!!!!!");
                Log.i(TAG, "开始下载图片!!!!!!!!!!");
                Log.i(TAG, "开始下载图片!!!!!!!!!!");

                //取出未下载的URL数据
                JudgeListData(ac);
                //开始下载
                Download(ac);

            } else {

                Log.i(TAG, MY_LOGCAT_TAG + "无法从服务端获取数据");

                Toast.makeText(ac, "无法从服务端获取数据...", Toast.LENGTH_SHORT).show();
                exit(ac);

            }
        }
    }


    private static void exit(final Activity ac) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    CommonUtil.Exit(ac);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * 拿未下载到本地的URL放入集合中,下载;
     * @param ac
     */
    public static void Download(final Activity ac) {

        if (listUrl.size() > 0) {

            new DownloadService(path, listUrl, new DownloadService.DownloadStateListener() {
                @Override
                public void onFinish() {

                    Log.i(TAG, "onFinish--图片已经全部下载成！！！！！！");

                    start(ac);
                }

                @Override
                public void onFailed() {

                    Log.i(TAG, "onFailed--有图片没有下载成功！");

                    start(ac);
                }
            }).startDownload();

        } else {


            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i(TAG, MY_LOGCAT_TAG + "没有新的图片下载,直接进入下单页面");
                    start(ac);
                }
            }).start();
        }
    }

    /**
     * 从本地文件中和获得的路径对比,筛选出未下载的URL;
     */
    public static void JudgeListData(Activity context) {

        fileUtils fu = new fileUtils(context);
        path = fu.getStorageDirectory() + File.separator;
        listUrl = new ArrayList<>();

        File f = null;

        for (int i = 0; i < dataList.size(); i++) {

            try {

                f = new File(path + CACHE_FILENAME_PREFIX + URLEncoder.encode(dataList.get(i).getFoodPicAddress().replace("*", ""), "UTF-8"));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!f.exists()) {

                listUrl.add(dataList.get(i).getFoodPicAddress());

            } else {

                Log.i(TAG, "文件已存在" + i);
            }
        }

    }


}
