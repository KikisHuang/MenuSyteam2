package com.menusystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.menusystem.R;
import com.menusystem.asynctask.OrderStateThread;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import static com.menusystem.bean.Verify.Ali_PAY;
import static com.menusystem.bean.Verify.WECHAT_PAY;

/**
 * Created by ${Kikis} on 2016-10-24.
 */

public class PayTheBillActivity extends Activity {
    private ImageView Wechat_img,AliPay_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paythebill_layout);
        init();
        setQRCode();
        queryrderState();
    }

    private void queryrderState() {

       String csid =  getIntent().getStringExtra("Pay_csid");
        if(!csid.isEmpty()){
            OrderStateThread ost = new OrderStateThread(csid,this);
            ost.start();
        }
    }

    private void setQRCode() {
        DisplayImageOptions options = com.menusystem.util.ImageLoader.getImageConfig();
        ImageLoader.getInstance().displayImage(Ali_PAY, AliPay_img, options);
        ImageLoader.getInstance().displayImage(WECHAT_PAY, Wechat_img, options);
    }


    private void init() {

        AliPay_img = (ImageView) findViewById(R.id.AliPay_img);
        Wechat_img  = (ImageView) findViewById(R.id.Wechat_img);

        setFinishOnTouchOutside(false);
    }
}
