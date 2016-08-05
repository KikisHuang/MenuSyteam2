package com.menusystem.util;

import android.graphics.Bitmap;

import static com.menusystem.util.LruCacheUtils.addBitmapToMemoryCache;
import static com.menusystem.util.LruCacheUtils.getBitmapFromMemCache;


/**
 * Created by ${Kikis} on 2016-07-29.
 */
public class ImageDownLoader {

    /**
     * 获取Bitmap, 内存中没有就去手机或者sd卡中获取，这一步在getView中会调用，比较关键的一步
     * @param url
     * @return
     */
    public static Bitmap showCacheBitmap(String url){

        if(getBitmapFromMemCache(url) != null){

            return getBitmapFromMemCache(url);

        }else if(fileUtils.isFileExists(url) && fileUtils.getFileSize(url) != 0){

            //从SD卡获取手机里面获取Bitmap
            Bitmap bitmap = fileUtils.getBitmap(url);

            //将Bitmap 加入内存缓存
            addBitmapToMemoryCache(url, bitmap);
            return bitmap;
        }

        return null;
    }

}
