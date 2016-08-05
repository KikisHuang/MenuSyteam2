package com.menusystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.menusystem.R;
import com.menusystem.bean.MenuType;
import com.menusystem.util.CommonUtil;

import java.util.List;

/**
 * Created by ${Kikis} on 2016-08-03.
 */
public class FoodTypeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<MenuType> list;

    public FoodTypeAdapter(Context context, List<MenuType> list) {
        this.context =context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


       ViewHolder viewHolder = null;

        View view =convertView;

        if(view==null){

            view = inflater.inflate(R.layout.left_menu_item,null);

            viewHolder  = new ViewHolder();

            viewHolder.LeftMenuName = (TextView) view.findViewById(R.id.LeftMenuName);

            view.setTag(viewHolder);

        }

        viewHolder = (ViewHolder) view.getTag();

        CommonUtil.setTextView(viewHolder.LeftMenuName,list.get(position).getFoodTypeName());



        return view;
    }


    class ViewHolder {
        private TextView LeftMenuName;
    }
}
