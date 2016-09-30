package com.menusystem.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by ${Kikis} on 2016-08-08.
 * <p>
 * 批量图片下载类 无需与界面交互的下载类
 */

public class DownloadService {

    public static String TAG = "DownloadService";
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    public static final String CACHE_FILENAME_PREFIX = "cache_";
    public static ExecutorService SINGLE_TASK_EXECUTOR = null;
    public static ExecutorService LIMITED_TASK_EXECUTOR = null;
    public static final ExecutorService FULL_TASK_EXECUTOR = null;
    public static final ExecutorService DEFAULT_TASK_EXECUTOR;
    public static Object lock = new Object();



    static {
        // SINGLE_TASK_EXECUTOR = (ExecutorService)
        // Executors.newSingleThreadExecutor();
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors
                .newFixedThreadPool(5);
        // FULL_TASK_EXECUTOR = (ExecutorService)
        // Executors.newCachedThreadPool();
        DEFAULT_TASK_EXECUTOR = LIMITED_TASK_EXECUTOR;

    }

    ;

    // 下载状态监听，提供回调
    static DownloadStateListener listener;
    // 下载目录
    private static String downloadPath;

    // 下载链接集合
    private static List<String> listURL;
    // 下载个数
    public static int size = 0;
    //下载失败个数;
    public static int FailedNumber = 0;
    // 下载完成回调接口
    public interface DownloadStateListener {
        public void onFinish();

        public void onFailed();
    }

    public DownloadService(String downloadPath, List<String> listURL,
                           DownloadStateListener listener) {
        this.downloadPath = downloadPath;
        this.listURL = listURL;
        this.listener = listener;
    }

    /**
     * 暂未提供设置
     */
    public void setDefaultExecutor() {

    }

    /**
     * 开始下载
     */
    public void startDownload() {

        // 首先检测path是否存在
        File downloadDirectory = new File(downloadPath);
        if (!downloadDirectory.exists()) {
            downloadDirectory.mkdirs();
        }
        try {

            for (int i = 0; i < listURL.size(); i++) {

                //捕获线程池拒绝执行异常
                // 线程放入线程池
//                 final int finalI = i;
//
//                DEFAULT_TASK_EXECUTOR.execute(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        downloadBitmap(listURL.get(finalI));
//                    }
//                });
                new downloadAsynckTask().execute(listURL.get(i));

            }
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            Log.e(TAG, "thread pool rejected error");
            listener.onFailed();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailed();
        }


    }


    /**
     * 下载图片
     *
     * @param urlString
     * @return
     */
    public static File downloadBitmap(String urlString) {
        String fileName = urlString;
        // 图片命名方式
        final File cacheFile = new File(createFilePath(new File(
                downloadPath), fileName));

        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            final InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(cacheFile),
                    IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            // 每下载成功一个，统计一下图片个数
            statDownloadNum();
            return cacheFile;

        } catch (final IOException e) {
            // 有一个下载失败，则表示批量下载没有成功
            Log.e(TAG, "download " + urlString + " error");
//            listener.onFailed();
            failedDownloadNum();
            return null;
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

    }

    public static String createFilePath(File cacheDir, String key) {
        try {
            // Use URLEncoder to ensure we have a valid filename, a tad hacky
            // but it will do for
            // this example

            Log.i(TAG, cacheDir.getAbsolutePath() + File.separator + CACHE_FILENAME_PREFIX
                    + URLEncoder.encode(key.replace("*", ""), "UTF-8"));


            return cacheDir.getAbsolutePath() + File.separator + CACHE_FILENAME_PREFIX
                    + URLEncoder.encode(key.replace("*", ""), "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            Log.e(TAG, "创建文件路径 - " + e);
        }

        return null;
    }

    /**
     * 统计下载个数
     */
    private static void statDownloadNum() {
        synchronized (lock) {
            size++;
            Log.d(TAG, "下载数量打印 " + size);
            if (size == listURL.size()||size+FailedNumber==listURL.size()) {
                Log.d(TAG, "download finished total  -- 下载成功统计 " + size+" ------ 下载失败统计 = "+FailedNumber);
                // 释放资源
                DEFAULT_TASK_EXECUTOR.shutdownNow();
                // 如果下载成功的个数与列表中 url个数一致，说明下载成功
                listener.onFinish(); // 下载成功回调
            }

        }
    }
    /**
     * 统计下载个数
     */
    private static void failedDownloadNum() {
        synchronized (lock) {
            FailedNumber++;
            if (size+FailedNumber==listURL.size()||FailedNumber==listURL.size()) {
                Log.d(TAG, "download failed total  -- 下载成功统计 = " + size+" ------ 下载失败统计 = "+FailedNumber);
                // 释放资源
                DEFAULT_TASK_EXECUTOR.shutdownNow();
                // 如果下载成功的个数加上失败个数与url中个数一直,回调方法;
                listener.onFailed(); // 下载失败回调
            }
        }
    }
    /**
     * AsyncTask方式;
     */
    public class downloadAsynckTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {


            String fileName = String.valueOf(params[0]);

            String urlString = String.valueOf(params[0]);

            // 图片命名方式
            final File cacheFile = new File(createFilePath(new File(
                    downloadPath), fileName));

            HttpURLConnection urlConnection = null;
            BufferedOutputStream out = null;

            try {
                final URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                final InputStream in = new BufferedInputStream(
                        urlConnection.getInputStream(), IO_BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(cacheFile),
                        IO_BUFFER_SIZE);

                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                // 每下载成功一个，统计一下图片个数
                statDownloadNum();
                return cacheFile;

            } catch (final IOException e) {
                // 有一个下载失败，则表示批量下载没有成功
                Log.e(TAG, "download " + urlString + " error");
//            listener.onFailed();
                failedDownloadNum();
                return null;
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

        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
