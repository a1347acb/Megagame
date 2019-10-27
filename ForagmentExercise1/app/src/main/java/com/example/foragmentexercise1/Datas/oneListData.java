package com.example.foragmentexercise1.Datas;

/**
 * 账户信息
 */
public class oneListData {
    private int number;//小车编号
    private int imageSrc;//图片资源ID
    private String plateNumber;//车牌号
    private String owner;//车主
    private int balance;//余额
    public oneListData(int number,int imageSrc,String plateNumber,String owner,int balance){
        this.number = number;
        this.imageSrc = imageSrc;
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getBalance() {
        return balance;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public String getOwner() {
        return owner;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
}
