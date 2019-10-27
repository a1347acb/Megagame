package com.example.foragmentexercise1.Datas;


import java.util.Comparator;

public class TransportationData implements Comparable<TransportationData>{
    private int busNumber;//公交车编号
    private int numberPeople;//承载人数
    private int minuteTime;//预计到达时间
    private int distance;//距离
    public TransportationData(int busNumber,int numberPeople,int minuteTime,int distance){
        this.busNumber = busNumber;
        this.numberPeople = numberPeople;
        this.minuteTime = minuteTime;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getMinuteTime() {
        return minuteTime;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setMinuteTime(int minuteTime) {
        this.minuteTime = minuteTime;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    @Override
    public int compareTo(TransportationData o) {
        if(distance > o.getDistance()){
            return 1;
        }else if(distance < o.getDistance()){
            return -1;
        }else{
            return 0;
        }
    }
}
