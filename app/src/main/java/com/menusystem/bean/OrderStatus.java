package com.menusystem.bean;

/**
 * Created by ${Kikis} on 2016-08-18.
 */
public class OrderStatus {

    String FoodID;
    int FoodNumber;
    int FoodStatus;
    int Append;


    public int getAppend() {
        return Append;
    }

    public void setAppend(int append) {
        Append = append;
    }

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public int getFoodNumber() {
        return FoodNumber;
    }

    public void setFoodNumber(int foodNumber) {
        FoodNumber = foodNumber;
    }

    public int getFoodStatus() {
        return FoodStatus;
    }

    public void setFoodStatus(int foodStatus) {
        FoodStatus = foodStatus;
    }


}
