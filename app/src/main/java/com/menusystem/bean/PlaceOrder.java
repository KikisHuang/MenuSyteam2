package com.menusystem.bean;

/**
 * Created by ${Kikis} on 2016-08-16.
 */
public class PlaceOrder {

    String SystemID;
    String CSID;
    String DeskID;
    int OrderStatus;
    double OrderCoupon;
    String StartDate;
    String StartTime;
    String EndDate;
    String EndTime;
    String SellPrice;
    int FoodStatus;
    String FoodId;
    int FoodNumber;
    String Detail;


    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public int getFoodNumber() {
        return FoodNumber;
    }

    public void setFoodNumber(int foodNumber) {
        FoodNumber = foodNumber;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getSystemID() {
        return SystemID;
    }

    public void setSystemID(String systemID) {
        SystemID = systemID;
    }

    public String getCSID() {
        return CSID;
    }

    public void setCSID(String CSID) {
        this.CSID = CSID;
    }

    public String getDeskID() {
        return DeskID;
    }

    public void setDeskID(String deskID) {
        DeskID = deskID;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public double getOrderCoupon() {
        return OrderCoupon;
    }

    public void setOrderCoupon(double orderCoupon) {
        OrderCoupon = orderCoupon;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(String sellPrice) {
        SellPrice = sellPrice;
    }

    public int getFoodStatus() {
        return FoodStatus;
    }

    public void setFoodStatus(int foodStatus) {
        FoodStatus = foodStatus;
    }

}
