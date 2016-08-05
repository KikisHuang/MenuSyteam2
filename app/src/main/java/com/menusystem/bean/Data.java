package com.menusystem.bean;

import java.io.Serializable;

/**
 * Created by ${Kikis} on 2016-07-28.
 */

public class Data implements Serializable {


    String SystemID;
    String FoodID;
    String FoodTypeID;
    String FoodName;
    String SellPrice;
    String FoodPicAddress;

    public String getSystemID() {
        return SystemID;
    }

    public void setSystemID(String systemID) {
        SystemID = systemID;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getFoodTypeID() {
        return FoodTypeID;
    }

    public void setFoodTypeID(String foodTypeID) {
        FoodTypeID = foodTypeID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodPicAddress() {
        return FoodPicAddress;
    }

    public void setFoodPicAddress(String foodPicAddress) {
        FoodPicAddress = foodPicAddress;
    }

    public String getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(String sellPrice) {
        SellPrice = sellPrice;
    }


}
