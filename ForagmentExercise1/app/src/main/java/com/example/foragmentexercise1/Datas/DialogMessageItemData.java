package com.example.foragmentexercise1.Datas;

import java.util.Comparator;

public class DialogMessageItemData{
    private String number;
    private String busNumber;
    private String peopleNumber;
    public DialogMessageItemData(String number,String busNumber,String peopleNumber){
        this.number = number;
        this.busNumber = busNumber;
        this.peopleNumber = peopleNumber;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getNumber() {
        return number;
    }

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
