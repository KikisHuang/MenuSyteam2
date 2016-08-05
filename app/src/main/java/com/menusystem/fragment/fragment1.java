package com.menusystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.menusystem.R;

import static com.menusystem.bean.Verify.MY_LOGCAT_TAG;

/**
 * Created by ${Kikis} on 2016-07-22.
 */

public class fragment1 extends Fragment {
    private static final String TAG = "Fragment1";
    private FlipViewController flipView;
    private View view;
    private RelativeLayout fv;
    String receive = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, null);
        fv = (RelativeLayout) view.findViewById(R.id.fv);
        getMybunlder();
//        Flipinit();
        Layoutinit();


        return view;
    }


    private void Layoutinit() {

        fv = (RelativeLayout) view.findViewById(R.id.fv);

        RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        flipView.setLayoutParams(ll);

        fv.addView(flipView);

    }

//    private void Flipinit() {
//
//        flipView = new FlipViewController(getActivity(), FlipViewController.HORIZONTAL);
//
//        flipView.setAdapter(new TravelAdapter(getActivity(), dlist));
//
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        flipView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        flipView.onPause();
//
//    }


    public void getMybunlder() {

        receive = getArguments().getString("id");

        if (!receive.equals("")) {

            Log.i(TAG, MY_LOGCAT_TAG + receive);

//            if(receive.equals("川菜")){
//
//                Flipinit();
//                Layoutinit();
//
//            }
//            if(receive.equals("饮品")){
//
//
//            }


            Toast.makeText(getActivity(), receive, Toast.LENGTH_SHORT).show();

        } else {

            Log.i(TAG, MY_LOGCAT_TAG + "Fragment receive null");
        }
    }
}
