package com.menusystem.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.menusystem.R;
import com.menusystem.adapter.FoodTypeAdapter;
import com.menusystem.adapter.TravelAdapter;
import com.menusystem.bean.Data;
import com.menusystem.bean.MenuType;
import com.menusystem.fragment.fragment1;
import com.menusystem.util.CommonUtil;
import com.menusystem.util.fileUtils;

import java.util.List;

import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private long mkeyTime;
    private ListView leftmenu;
    List<MenuType> list;
    List<Data> dlist;
    // fragment管理器
    FragmentManager fm = this.getSupportFragmentManager();
    FragmentTransaction ft;
    MenuType mt;
    Data dt;
    RelativeLayout MainRl;
    private FlipViewController flipView;
    Context context;
    TravelAdapter tadapter;
    FoodTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        receive();
        Flipinit();
        Layoutinit();

        //开始Fragment事务
        //one();

    }

    private void receive() {

        list = (List<MenuType>) getIntent().getSerializableExtra("menu_type");

        dlist = (List<Data>) getIntent().getSerializableExtra("food_menu");


        mt = new MenuType();
        dt = new Data();


         adapter = new FoodTypeAdapter(MainActivity.this, list);

        leftmenu.setAdapter(adapter);

        leftmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {


                for (int i = 0; i < leftmenu.getChildCount(); i++) {

                    if (i == position) {

                        leftmenu.getChildAt(i).setBackgroundColor(MainActivity.this.getResources().getColor(R.color.black5));
                    } else {

                        leftmenu.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }

               String a =  list.get(position).getFoodTypeID();


                flipView.setSelection(position);

                tadapter.notifyDataSetInvalidated();

            }
        });

    }

    private void Layoutinit() {


        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        flipView.setLayoutParams(ll);

        MainRl.addView(flipView);

    }

    private void Flipinit() {

        flipView = new FlipViewController(MainActivity.this, FlipViewController.HORIZONTAL);

        tadapter  = new TravelAdapter(MainActivity.this,dlist);

        flipView.setAdapter(tadapter);

    }


    private void iniFragment(String num) {


        ft = fm.beginTransaction();

        Bundle bd = new Bundle();
        bd.putString("id", num);
        fragment1 f1 = new fragment1();

        f1.setArguments(bd);
        switchFragment(f1);

    }

    private void switchFragment(Fragment fragment) {


//        ft = getSupportFragmentManager().beginTransaction();


        if (!fragment.isAdded()) {

            ft.add(R.id.MainRl, fragment).commit();

        } else {

            ft.show(fragment).commit();
        }

    }


    private void init() {

        leftmenu = (ListView) findViewById(R.id.leftmenu);
        MainRl = (RelativeLayout) findViewById(R.id.MainRl);

    }

    @Override
    public void onClick(View view) {

    }

    private void one() {
        ft = fm.beginTransaction();

        if (list.size() > 0) {

            mt = list.get(1);

            String num = mt.getFoodTypeName();

            Log.i(TAG, MY_LOGCAT_TAG +"获得首页的FoodTypeName为:" + num);

            iniFragment(num);


        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /**二次点击退出*/
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            } else {

                fileUtils fl = new fileUtils(this);
                fl.deleteFile();

                CommonUtil.entirelyexit(this);

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 生命周期logcat
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,MY_LOGCAT_TAG+"onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,MY_LOGCAT_TAG+"onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,MY_LOGCAT_TAG+"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,MY_LOGCAT_TAG+"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,MY_LOGCAT_TAG+"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,MY_LOGCAT_TAG+"onDestroy");

    }
}
