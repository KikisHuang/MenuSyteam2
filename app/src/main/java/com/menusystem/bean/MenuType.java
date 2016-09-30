package com.menusystem.bean;

import java.io.Serializable;

/**
 * Created by ${Kikis} on 2016-08-01.
 */
public class MenuType implements Serializable{
    String SystemID;
    String FoodTypeID;
    String FoodTypeName;

    public String getSystemID() {
        return SystemID;
    }

    public void setSystemID(String systemID) {
        SystemID = systemID;
    }

    public String getFoodTypeID() {
        return FoodTypeID;
    }

    public void setFoodTypeID(String foodTypeID) {
        FoodTypeID = foodTypeID;
    }

    public String getFoodTypeName() {
        return FoodTypeName;
    }

    public void setFoodTypeName(String foodTypeName) {
        FoodTypeName = foodTypeName;
    }



}
