package com.menusystem.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.menusystem.bean.Order;

import java.util.List;

/**
 * Created by ${Kikis} on 2016-08-19.
 */

public class DatabaseUtils {
    private static final String TAG = "DatabaseUtils";
    static SQLiteDatabase db = null;
    static DatabaseHelper dbHelper = null;
    static Context context;


    public DatabaseUtils(Context context) {

        this.context = context;

    }

    /**
     * 新建一个数据库
     *
     * @param dbName
     * @return
     */
    public static void CreateDatabase(String dbName) {

        dbHelper = new DatabaseHelper(context, dbName);
        db = dbHelper.getWritableDatabase();

    }


    /**
     * 新建一个java.lang.String表
     *
     * @param
     */
    public static void CreateTable() {
        db = dbHelper.getWritableDatabase();
        String sql = "create table Menu" +
                "(String FoodName String SellPrice,name varchar,sex varchar)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.i(TAG + "err", "create table failed");
        }
    }

    /**
     * 插入数据
     *
     * @param foodName
     * @param sellPrice
     * @param foodId
     * @param append
     * @param number
     * @param state
     */
    public static void InsertTb(String foodName, String sellPrice, String foodId, int append, int number, int state) {
        db = dbHelper.getWritableDatabase();

        String sql = "insert into CCG (FoodName,SellPrice,FoodId,Append,Number,State) values ('" + foodName + "','" + sellPrice + "','" + foodId + "'," + append + "," + number + "," + state + ")";

        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.i(TAG + "err", "insert failed");
        }
    }


    /**
     * 查询数据
     *
     * @param olist
     */

    public static List<Order> QueryData(List<Order> olist) {

        String que = "select * from  CCG;";

        Cursor cursor = db.rawQuery(que, null);


        if (cursor != null && cursor.getCount() >= 1) {

            while (cursor.moveToNext()) {

                String FoodName = cursor.getString(0);
                String SellPrice = cursor.getString(1);
                String FoodId = cursor.getString(2);
                int Append = cursor.getInt(3);
                int Number = cursor.getInt(4);
                int State = cursor.getInt(5);

                Order od = new Order();

                od.setFoodName(FoodName);
                od.setSellPrice(SellPrice);
                od.setFoodId(FoodId);
                od.setAppend(Append);
                od.setNumber(Number);
                od.setState(State);

                Log.i(TAG, od.getFoodName() + "-" + od.getSellPrice() + "-" + od.getFoodId() + "-" + od.getAppend() + "-" + od.getNumber() + "-" + od.getState());

                olist.add(od);
            }

            cursor.close();
            return olist;
        }
        cursor.close();
        return olist;
    }

    /**
     * 更新数据
     */
    public void UpdateTb() {
        db = dbHelper.getWritableDatabase();
        String sql = "Update TestUsers set name = 'anhong',sex = 'men' where id = 2";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.i(TAG + "err", "update failed");
        }


    }


    /**
     * 删除数据
     */
    public static void DeleteTb() {
        db = dbHelper.getWritableDatabase();
        String sql = "delete from CCG";
        try {
            db.execSQL(sql);
            Log.i(TAG,"删除数据库");
        } catch (SQLException e) {
            Log.i(TAG + "err", "delete failed");
        }
    }

    /**
     * 关闭数据库
     */
    public static void CloseDb() {
        dbHelper.close();
    }

    /**
     * 打开数据库
     */
    public void OpenDb() {
        dbHelper = new DatabaseHelper(context, "TestDb01");
        db = dbHelper.getWritableDatabase();
    }

}
