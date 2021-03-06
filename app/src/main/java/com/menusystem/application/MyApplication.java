package com.menusystem.application;

import android.app.Activity;
import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Kikis} on 2016-07-22.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    public static List<Object> activitys = new ArrayList<Object>();
    private static MyApplication instance;


    public static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (!activitys.contains(activity))
            activitys.add(activity);
    }

    // 遍历所有Activity并finish
    public void destroy() {

        for (Object activity : activitys) {
            ((Activity) activity).finish();
        }
        System.exit(0);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * ImagerLoader配置
         */
        //缓存文件的存放地址
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)// max width, max height
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//降低线程的优先级保证主UI线程不受太大影响
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))//建议内存设在5-10M,可以有比较好的表现
                .memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024) // 缓存大小
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCacheFileCount(100)// 缓存文件数目
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiskCache(cacheDir))
                .writeDebugLogs() //打印log信息
                .build();

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

    }

}
