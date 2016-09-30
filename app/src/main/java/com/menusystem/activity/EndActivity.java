package com.menusystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.menusystem.R;

import static com.menusystem.util.CommonUtil.addActivity;
import static com.menusystem.util.CommonUtil.entirelyexit;

/**
 * Created by ${Kikis} on 2016-08-22.
 */
public class EndActivity extends Activity {

    private static final String TAG = "EndActivity";
    ImageView imageView;
    public static final int ANIMATION_TIME = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
        addActivity(this);
        End();
    }

    private void End() {

        Log.i(TAG, "进入EndActivity启动结束动画...");
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setBackgroundResource(R.drawable.thanks_for_coming);

        //图片渐变模糊度始终
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);

        //渐变时间
        aa.setDuration(ANIMATION_TIME);

        //展示图片渐变动画
        imageView.startAnimation(aa);

        //渐变过程监听
        aa.setAnimationListener(new Animation.AnimationListener() {

            /**
             * 动画开始时
             */
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("动画开始...");
            }

            /**
             * 重复动画时
             */
            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("动画重复...");

            }

            /**
             * 动画结束时
             */
            @Override
            public void onAnimationEnd(Animation animation) {

                System.out.println("动画结束...");

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            Thread.sleep(3000);

                            restartApplication();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"destroyActivity .................");
    }

    private void restartApplication() {
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        entirelyexit();
    }
}
