package com.menusystem.util;

import android.graphics.Bitmap;

import com.menusystem.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by ${Kikis} on 2016-08-05.
 */

public class ImageLoader {
    /**
     *  Kikis
     *   ImageLoader Config example
     *
     * DisplayImageOptions options = new DisplayImageOptions.Builder()
     * .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
     * .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
     * .showImageOnFail(R.drawable.ic_error) // resource or drawable
     * .resetViewBeforeLoading(false)  // default
     * .delayBeforeLoading(1000)
     * .cacheInMemory(false) // default
     * .cacheOnDisk(false) // default
     * .preProcessor(...)
     * .postProcessor(...)
     * .extraForDownloader(...)
     * .considerExifParams(false) // default
     * .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
     * .bitmapConfig(Bitmap.Config.ARGB_8888) // default
     * .decodingOptions(...)
     * .displayer(new SimpleBitmapDisplayer()) // default
     * .handler(new Handler()) // default
     * .build()
     */

    public static DisplayImageOptions getImageConfig() {

        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(100)
                .build();


        return options;
    }



}
