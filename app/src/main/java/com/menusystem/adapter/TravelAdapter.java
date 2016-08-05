package com.menusystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.menusystem.R;
import com.menusystem.bean.Data;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;


public class TravelAdapter extends BaseAdapter {

    public static final String TAG = "TravelAdapter";

    private LayoutInflater inflater;
    private int repeatCount = 1;
    private Context mcontext;

    public static List<Data> list;

    public TravelAdapter(Context context, List<Data> dlist) {
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

        int mposition = 0;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.complex1, null);

            holder = new ViewHolder();

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


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

//        final Data data = travelData.get(position % travelData.size());

        int da = list.size();

        Log.i(TAG, "data ===========" + da);

        holder.number.setText(position + 1 + "/" + da);


        holder.price.setText(list.get(position).getSellPrice());
        holder.price1.setText(list.get(position).getSellPrice());
        holder.price2.setText(list.get(position).getSellPrice());
        holder.dish_name.setText(list.get(position).getFoodName());
        holder.dish_name1.setText(list.get(position).getFoodName());
        holder.dish_name2.setText(list.get(position).getFoodName());

        String key = list.get(position).getFoodID();
        String key1 = list.get(position).getFoodID();
        String key2 = list.get(position).getFoodID();

        Log.i(TAG, "" + key);

        Log.i(TAG, "---------" + list.get(position).getFoodPicAddress());
        Log.i(TAG, "---------" + list.get(position).getFoodPicAddress());
        Log.i(TAG, "---------" + list.get(position).getFoodPicAddress());


        DisplayImageOptions options = com.menusystem.util.ImageLoader.getImageConfig();


//            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress(), holder.photo1, options);
//            ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress(), holder.photo2, options);

        ImageAware imageAware = new ImageViewAware(holder.photo,false);
        ImageAware imageAware1 =new ImageViewAware(holder.photo1,false);
        ImageAware imageAware2 =new ImageViewAware(holder.photo2,false);

        ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress(), imageAware,options);
        ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress(),imageAware1,options);
        ImageLoader.getInstance().displayImage(list.get(position).getFoodPicAddress(),imageAware2,options);


        convertView.setTag(holder);

        Log.i(TAG, "获取系统分配给每个应用程序的最大内存" + String.valueOf(Runtime.getRuntime().maxMemory() / 1024 / 1024) + "M");
        Log.i(TAG, "应用程序已获得内存" + String.valueOf(Runtime.getRuntime().totalMemory() / 1024 / 1024) + "M");
        Log.i(TAG, "应用程序已获得内存中未使用内存" + String.valueOf(Runtime.getRuntime().freeMemory() / 1024 / 1024) + "M");


        return convertView;
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
}
