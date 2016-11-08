package com.menusystem.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.menusystem.R;
import com.menusystem.bean.Three;
import com.menusystem.util.LruCacheUtils;
import com.menusystem.util.OrderIntentUtils;
import com.menusystem.util.fileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.menusystem.R.id.price1;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.util.OrderIntentUtils.getIntentOrder;


public class TravelAdapter extends BaseAdapter {

    private String URL1, URL2, URL3;
    public static final String TAG = "TravelAdapter";
    private static final String CACHE_FILENAME_PREFIX = "cache_";
    private LayoutInflater inflater;
    private int repeatCount = 1;
    private static Activity mcontext;
    private String path = "";
    public static List<Three> list;
    public List<String> mlist;
    public static Bitmap bt1;
    public static Bitmap bt2;
    public static Bitmap bt3;

    public TravelAdapter(Activity context, List<Three> dlist) {
        this.mcontext = context;
        this.list = dlist;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//    View layout = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.complex1, null);

            holder = new ViewHolder();

            init(holder, convertView);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        getPath();
        holderSet(holder, position);
        convertView.setTag(holder);
        ClickListener(holder, position);

        printMemory();

        return convertView;
    }

    private void ClickListener(ViewHolder holder, final int position) {


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fdname = list.get(position).getFoodName1();
                String fdid = list.get(position).getFoodID1();
                String Sp = list.get(position).getSellPrice1();

                new OrderIntentUtils(mcontext, fdname, fdid, Sp);
                getIntentOrder();

            }
        });

        if(!URL2.equals("")){

            holder.photo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fdname = list.get(position).getFoodName2();
                    String fdid = list.get(position).getFoodID2();
                    String Sp = list.get(position).getSellPrice2();

                    new OrderIntentUtils(mcontext, fdname, fdid, Sp);
                    getIntentOrder();

                }
            });
        }


        if(!URL.equals("")){
            holder.photo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fdname = list.get(position).getFoodName3();
                    String fdid = list.get(position).getFoodID3();
                    String Sp = list.get(position).getSellPrice3();

                    new OrderIntentUtils(mcontext, fdname, fdid, Sp);
                    getIntentOrder();

                }
            });
        }

    }

    private void getPath() {

        fileUtils fu = new fileUtils(mcontext);

        path = fu.getStorageDirectory() + File.separator;
    }

    private void printMemory() {

        Log.i(TAG, "获取系统分配给每个应用程序的最大内存" + String.valueOf(Runtime.getRuntime().maxMemory() / 1024 / 1024) + "M");
        Log.i(TAG, "应用程序已获得内存" + String.valueOf(Runtime.getRuntime().totalMemory() / 1024 / 1024) + "M");
        Log.i(TAG, "应用程序已获得内存中未使用内存" + String.valueOf(Runtime.getRuntime().freeMemory() / 1024 / 1024) + "M");
    }


    /**
     * Holder.set
     */
    private void holderSet(ViewHolder holder, int position) {

        int da = list.size();

        Log.i(TAG, "data ===========" + da);

        holder.number.setText(position + 1 + "/" + da);

        holder.price.setText(list.get(position).getSellPrice1());
        holder.price1.setText(list.get(position).getSellPrice2());
        holder.price2.setText(list.get(position).getSellPrice3());
        holder.dish_name.setText(list.get(position).getFoodName1());
        holder.dish_name1.setText(list.get(position).getFoodName2());
        holder.dish_name2.setText(list.get(position).getFoodName3());

        try {

            /**
             * 将路径进行替代显示
             */
            URL1 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress1().replace("*", ""), "UTF-8");

            if(list.get(position).getFoodPicAddress2().equals("")){

                URL2 = "";
            }else{
                URL2 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress2().replace("*", ""), "UTF-8");

            }
            if(list.get(position).getFoodPicAddress3().equals("")){

                URL3 = "";

            }else{
                URL3 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress3().replace("*", ""), "UTF-8");

            }

        } catch (Exception e) {
            e.printStackTrace();


            Log.i(TAG, MY_LOGCAT_TAG + " 异常信息++++++++s" + e);
        }
        fileUtils fu = new fileUtils(mcontext);

        LruCacheUtils Lcu = new LruCacheUtils();

        /**
         * 从本地或sdk中获取数据
         */
        bt1 = fu.getBitmap(URL1);

        if(URL2.equals("")){
            bt2 = null;
        }else{
            bt2 = fu.getBitmap(URL2);
        }
        if(URL3.equals("")){
            bt3 = null;
        }else{
            bt3 = fu.getBitmap(URL3);
        }

        /**
         *ImageLoader options配置
         */
        DisplayImageOptions options = com.menusystem.util.ImageLoader.getImageConfig();

        if (bt1 != null) {

            Bitmap bitmap = Lcu.getBitmapFromMemCache(URL1);

            if (bitmap == null) {

                Lcu.addBitmapToMemoryCache(URL1, bt1);

                holder.photo.setImageBitmap(Lcu.getBitmapFromMemCache(URL1));

            } else {

                holder.photo.setImageBitmap(Lcu.getBitmapFromMemCache(URL1));

            }

        } else {
            /**
             * ImageLoader框架displayImage方法加载图片
             */
            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress1(), holder.photo, options);

        }

        if (bt2 != null) {

            Bitmap bitmap = Lcu.getBitmapFromMemCache(URL2);

            if (bitmap == null) {

                Lcu.addBitmapToMemoryCache(URL2, bt2);

                holder.photo1.setImageBitmap(Lcu.getBitmapFromMemCache(URL2));
            } else {
                holder.photo1.setImageBitmap(Lcu.getBitmapFromMemCache(URL2));
            }
        } else {

            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress2(), holder.photo1, options);

        }


        if (bt3 != null) {

            Bitmap bitmap = Lcu.getBitmapFromMemCache(URL3);

            if (bitmap == null) {

                Lcu.addBitmapToMemoryCache(URL3, bt3);

                holder.photo2.setImageBitmap(Lcu.getBitmapFromMemCache(URL3));

            } else {

                holder.photo2.setImageBitmap(Lcu.getBitmapFromMemCache(URL3));
            }


        } else {

            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress3(), holder.photo2, options);

        }

        RemoveCache();
    }


    /**
     * 缓存清理方法
     */
    private void RemoveCache() {


        int Chace = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);

        if (Chace > 80) {

            Log.i(TAG, "缓存超过！！！当前缓存为:" + Chace + "-----------");

            LruCacheUtils lcu = new LruCacheUtils();

            lcu.clearCache();
            System.gc();

            int Chace1 = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);
            Log.i(TAG, "成功清理缓存！！！当前缓存为:" + Chace1 + "-----------");
        }
    }

    /**
     * initialize
     */
    private void init(ViewHolder holder, View convertView) {


        holder.number = (TextView) convertView.findViewById(R.id.number);

        holder.photo = (ImageView) convertView.findViewById(R.id.photo);
        holder.price = (TextView) convertView.findViewById(R.id.price);
        holder.dish_name = (TextView) convertView.findViewById(R.id.dish_name);

        holder.photo1 = (ImageView) convertView.findViewById(R.id.photo1);
        holder.price1 = (TextView) convertView.findViewById(price1);
        holder.dish_name1 = (TextView) convertView.findViewById(R.id.dish_name1);

        holder.photo2 = (ImageView) convertView.findViewById(R.id.photo2);
        holder.price2 = (TextView) convertView.findViewById(R.id.price2);
        holder.dish_name2 = (TextView) convertView.findViewById(R.id.dish_name2);

    }

    static class ViewHolder {

        TextView number;

        TextView dish_name;
        TextView price;
        ImageView photo;

        TextView dish_name1;
        TextView price1;
        ImageView photo1;

        TextView dish_name2;
        TextView price2;
        ImageView photo2;

    }

    //BitmapRecycle;
    public static void BitmapRecycle() {

        if (bt1 != null && !bt1.isRecycled()) {
            bt1.recycle();
            bt1 = null;
            Log.i(TAG,"Recycle Bitmap...............");
        }
        if (bt2 != null && !bt2.isRecycled()) {
            bt2.recycle();
            bt2 = null;
        }
        if (bt3 != null && !bt3.isRecycled()) {
            bt3.recycle();
            bt3 = null;
        }

        System.gc();

    }
}
