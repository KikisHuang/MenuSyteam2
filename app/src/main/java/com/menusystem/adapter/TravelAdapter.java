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
import com.menusystem.bean.Data;
import com.menusystem.bean.Four;
import com.menusystem.util.LruCacheUtils;
import com.menusystem.util.OrderIntentUtils;
import com.menusystem.util.fileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;
import static com.menusystem.util.DownloadService.DEFAULT_TASK_EXECUTOR;
import static com.menusystem.util.DownloadService.IO_BUFFER_SIZE;
import static com.menusystem.util.DownloadService.createFilePath;
import static com.menusystem.util.DownloadService.lock;
import static com.menusystem.util.DownloadService.size;
import static com.menusystem.util.OrderIntentUtils.getIntentOrder;


public class TravelAdapter extends BaseAdapter {

    private String URL1, URL2, URL3, URL4;
    public static final String TAG = "TravelAdapter";
    private static final String CACHE_FILENAME_PREFIX = "cache_";
    private LayoutInflater inflater;
    private int repeatCount = 1;
    private static Activity mcontext;
    private String path = "";
    public static List<Four> list;
    private List<Data> datas;
    public List<String> mlist;
    public static Bitmap bt1;
    public static Bitmap bt2;
    public static Bitmap bt3;
    public static Bitmap bt4;

    public TravelAdapter(Activity context, List<Four> dlist, List<Data> datas) {
        this.mcontext = context;
        this.list = dlist;
        this.datas = datas;
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

        holder.photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fdname = list.get(position).getFoodName4();
                String fdid = list.get(position).getFoodID4();
                String Sp = list.get(position).getSellPrice4();

                new OrderIntentUtils(mcontext, fdname, fdid, Sp);
                getIntentOrder();

            }
        });
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
        holder.price3.setText(list.get(position).getSellPrice4());
        holder.dish_name.setText(list.get(position).getFoodName1());
        holder.dish_name1.setText(list.get(position).getFoodName2());
        holder.dish_name2.setText(list.get(position).getFoodName3());
        holder.dish_name3.setText(list.get(position).getFoodName4());

        try {

            /**
             * 将路径进行替代显示
             */
            URL1 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress1().replace("*", ""), "UTF-8");
            URL2 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress2().replace("*", ""), "UTF-8");
            URL3 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress3().replace("*", ""), "UTF-8");
            URL4 = CACHE_FILENAME_PREFIX + URLEncoder.encode(list.get(position).getFoodPicAddress4().replace("*", ""), "UTF-8");

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
        bt2 = fu.getBitmap(URL2);
        bt3 = fu.getBitmap(URL3);
        bt4 = fu.getBitmap(URL4);

        /**
         * ImageLoader options配置
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


        if (bt4 != null) {

            Bitmap bitmap = Lcu.getBitmapFromMemCache(URL4);

            if (bitmap == null) {

                Lcu.addBitmapToMemoryCache(URL4, bt4);

                holder.photo3.setImageBitmap(Lcu.getBitmapFromMemCache(URL4));

            } else {

                holder.photo3.setImageBitmap(Lcu.getBitmapFromMemCache(URL4));
            }


        } else {

            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress4(), holder.photo3, options);

        }


        RemoveCache();
    }


    /**
     * 调用下载类DownloadService下载单个图片资源
     */

    private void StartDownload(int position, Bitmap bt1, Bitmap bt2, Bitmap bt3) {

        Log.i(TAG, MY_LOGCAT_TAG + "进入单张图片资源下载方法，id===" + id);
        mlist = new ArrayList<>();


        if (bt1 == null) {
            mlist.add(list.get(position).getFoodPicAddress1());

            Log.i(TAG, "getFoodPicAddress1-----为null" + list.get(position).getFoodPicAddress1());
        }

        if (bt2 == null) {
            mlist.add(list.get(position).getFoodPicAddress2());

            Log.i(TAG, "getFoodPicAddress2-----为null" + list.get(position).getFoodPicAddress2());
        }

        if (bt3 == null) {
            mlist.add(list.get(position).getFoodPicAddress3());

            Log.i(TAG, "getFoodPicAddress3-----为null" + list.get(position).getFoodPicAddress3());
        }

        if (mlist.size() > 0) {

            for (int i = 0; i < mlist.size(); i++) {

                final int finalI = i;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        MyBasedownload(mlist.get(finalI));

                    }
                }).start();


            }
        }

    }

    private File MyBasedownload(String murl) {

        fileUtils fu = new fileUtils(mcontext);
        path = fu.getStorageDirectory() + File.separator;


        String fileName = murl;
        // 图片命名方式
        final File cacheFile = new File(createFilePath(new File(
                path), fileName));

        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;

        try {
            final URL url = new URL(murl);
            urlConnection = (HttpURLConnection) url.openConnection();
            final InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(cacheFile),
                    IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            statDownloadNum();
            return cacheFile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // 有一个下载失败，则表示批量下载没有成功
            Log.e(TAG, "下载失败了....... " + murl + " error信息=====" + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error in downloadBitmap - " + e);
                }
            }
        }
        return cacheFile;
    }

    private void statDownloadNum() {

        synchronized (lock) {
            size++;
            if (size == mlist.size()) {
                Log.d(TAG, "download finished total  -- 下载成功统计 " + size);
                // 释放资源
                DEFAULT_TASK_EXECUTOR.shutdownNow();
                // 如果下载成功的个数与列表中 url个数一致，说明下载成功

                Log.i(TAG, "下载成功了:" + size + "个本地没有的图片");
            }
        }
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
        holder.price1 = (TextView) convertView.findViewById(R.id.price1);
        holder.dish_name1 = (TextView) convertView.findViewById(R.id.dish_name1);

        holder.photo2 = (ImageView) convertView.findViewById(R.id.photo2);
        holder.price2 = (TextView) convertView.findViewById(R.id.price2);
        holder.dish_name2 = (TextView) convertView.findViewById(R.id.dish_name2);

        holder.photo3 = (ImageView) convertView.findViewById(R.id.photo3);
        holder.dish_name3 = (TextView) convertView.findViewById(R.id.dish_name3);
        holder.price3 = (TextView) convertView.findViewById(R.id.price3);

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

        TextView dish_name3;
        TextView price3;
        ImageView photo3;
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
        if (bt4 != null && !bt4.isRecycled()) {
            bt4.recycle();
            bt4 = null;
        }

        System.gc();

    }
}
