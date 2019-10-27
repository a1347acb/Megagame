package com.example.foragmentexercise1.Datas;

/**
 * 充值记录界面ListView的item数据对象
 */
public class recordData {
    public recordData(String plateNumber, double money, String dateTime) {
        this.plateNumber = plateNumber;
        this.money = money;
        this.dateTime = dateTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String plateNumber;//车牌
    private double money;//充值金额
    private String dateTime;//充值日期

}
