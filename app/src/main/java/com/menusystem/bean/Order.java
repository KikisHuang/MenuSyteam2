package com.menusystem.bean;

import java.io.Serializable;

/**
 * Created by ${Kikis} on 2016-08-11.
 */
public class Order implements Serializable {


    String FoodName;
    String SellPrice;
    String FoodId;
    int Append;

    int Number;
    int State;


    public int getAppend() {
        return Append;
    }

    public void setAppend(int append) {
        Append = append;
    }
    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(String sellPrice) {
        SellPrice = sellPrice;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

}
