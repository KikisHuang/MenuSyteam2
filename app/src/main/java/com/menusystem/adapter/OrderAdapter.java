package com.menusystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.menusystem.R;
import com.menusystem.bean.Order;
import com.menusystem.view.SliderChangeView;

import java.util.List;

import static com.menusystem.bean.Verify.NO_PLACE_AN_ORDER;
import static com.menusystem.util.AlertDialogUtils.getDeleteDialog;

/**
 * Created by ${Kikis} on 2016-08-12.
 */
public class OrderAdapter extends BaseAdapter {
    private static final String TAG = "OrderAdapter";
    private Context context;
    private LayoutInflater inflater;
    List<Order> olist;


    public OrderAdapter(Context context, List<Order> list) {
        this.context = context;
        this.olist = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return olist.size();
    }

    @Override
    public Object getItem(int position) {
        return olist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;
        //自定义侧滑删除view
        SliderChangeView sliderview = (SliderChangeView) convertView;

        if (sliderview == null) {

            View view = inflater.inflate(R.layout.order_item, null);

            holder = new ViewHolder();

            //自定义侧滑删除view
            sliderview = new SliderChangeView(context);
            sliderview.setContentView(view);


            holder.order_name = (TextView) sliderview.findViewById(R.id.order_name);
            holder.order_price = (TextView) sliderview.findViewById(R.id.order_price);
            holder.order_num = (TextView) sliderview.findViewById(R.id.order_num);
            holder.deleteHolder = (ViewGroup) sliderview.findViewById(R.id.holder);

            sliderview.setTag(holder);

        }

        holder = (ViewHolder) sliderview.getTag();

        sliderview.shrink();

        DeleteButton(holder, position, sliderview);


        int StateJudge = olist.get(position).getState();

//        if (StateJudge == PLACE_AN_ORDER) {
//
//
//        }
        if (StateJudge == NO_PLACE_AN_ORDER) {

            NoPlaceOrderState(holder, position);

        }else{

            PlaceOrderState(holder, position);
        }

//        if (StateJudge == OFF_THE_STOCKS) {
//
//            OffTheStocks(holder, position);
//
//        }

//        holder.order_name.setText(olist.get(position).getFoodName());
//        holder.order_price.setText(olist.get(position).getSellPrice());
//        holder.order_num.setText(String.valueOf(olist.get(position).getNumber()));

        return sliderview;
    }

    private void DeleteButton(ViewHolder holder, final int position, final SliderChangeView sliderview) {

        holder.deleteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDeleteDialog(context,olist,position,sliderview,OrderAdapter.this);

            }
        });

    }

    /**
     * 已上菜状态9;
     */
    private void OffTheStocks(ViewHolder holder, int position) {

        int Color = context.getResources().getColor(R.color.blue);

        holder.order_name.setText(olist.get(position).getFoodName());
        holder.order_name.setTextColor(Color);

        holder.order_price.setText(olist.get(position).getSellPrice());
        holder.order_price.setTextColor(Color);

        holder.order_num.setText(String.valueOf(olist.get(position).getNumber()));
        holder.order_num.setTextColor(Color);

    }

    /**
     * 未下单状态0;
     */
    private void NoPlaceOrderState(ViewHolder holder, int position) {

        String remark = olist.get(position).getDetailName();

        if(remark.equals("默认")){
            remark = "";
        }else{
            remark = "("+olist.get(position).getDetailName()+")";
        }

        int Color = context.getResources().getColor(R.color.black);

        holder.order_name.setText(olist.get(position).getFoodName()+remark);
        holder.order_name.setTextColor(Color);

        holder.order_price.setText(olist.get(position).getSellPrice());
        holder.order_price.setTextColor(Color);

        holder.order_num.setText(String.valueOf(olist.get(position).getNumber()));
        holder.order_num.setTextColor(Color);

    }

    /**
     * 已下单状态1;
     */
    private void PlaceOrderState(ViewHolder holder, int position) {


        String remark = olist.get(position).getDetailName();

        if(remark.equals("默认")){
            remark = "";
        }else{
            remark = "("+olist.get(position).getDetailName()+")";
        }


        int Color = context.getResources().getColor(R.color.blue);

        holder.order_name.setText(olist.get(position).getFoodName()+remark);
        holder.order_name.setTextColor(Color);

        holder.order_price.setText(olist.get(position).getSellPrice());
        holder.order_price.setTextColor(Color);

        holder.order_num.setText(String.valueOf(olist.get(position).getNumber()));
        holder.order_num.setTextColor(Color);

    }


    class ViewHolder {
        private TextView order_name;
        private TextView order_price;
        private TextView order_num;
        private ViewGroup deleteHolder;
    }

}
