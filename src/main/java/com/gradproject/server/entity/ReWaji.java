package com.gradproject.server.entity;

import java.io.Serializable;

public class ReWaji implements Serializable {
    private Integer id;

    private String date;

    private String carId;

    private String carType;

    private String type;

    private String ownerName;

    private String oilcarNum;

    private String oilL;

    private String oilTime;

    private String oilPrice;

    private String num;

    private String beilv;

    private String wuliao;

    private String wajidanjia;

    private String shijianxuanze;

    private String jishishijian;

    private String jishidanjia;

    private String zhuangmeiche;

    private String zhuangmeidanjia;

    private String yun;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOilcarNum() {
        return oilcarNum;
    }

    public void setOilcarNum(String oilcarNum) {
        this.oilcarNum = oilcarNum;
    }

    public String getOilL() {
        return oilL;
    }

    public void setOilL(String oilL) {
        this.oilL = oilL;
    }

    public String getOilTime() {
        return oilTime;
    }

    public void setOilTime(String oilTime) {
        this.oilTime = oilTime;
    }

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBeilv() {
        return beilv;
    }

    public void setBeilv(String beilv) {
        this.beilv = beilv;
    }

    public String getWuliao() {
        return wuliao;
    }

    public void setWuliao(String wuliao) {
        this.wuliao = wuliao;
    }

    public String getWajidanjia() {
        return wajidanjia;
    }

    public void setWajidanjia(String wajidanjia) {
        this.wajidanjia = wajidanjia;
    }

    public String getShijianxuanze() {
        return shijianxuanze;
    }

    public void setShijianxuanze(String shijianxuanze) {
        this.shijianxuanze = shijianxuanze;
    }

    public String getJishishijian() {
        return jishishijian;
    }

    public void setJishishijian(String jishishijian) {
        this.jishishijian = jishishijian;
    }

    public String getJishidanjia() {
        return jishidanjia;
    }

    public void setJishidanjia(String jishidanjia) {
        this.jishidanjia = jishidanjia;
    }

    public String getZhuangmeiche() {
        return zhuangmeiche;
    }

    public void setZhuangmeiche(String zhuangmeiche) {
        this.zhuangmeiche = zhuangmeiche;
    }

    public String getZhuangmeidanjia() {
        return zhuangmeidanjia;
    }

    public void setZhuangmeidanjia(String zhuangmeidanjia) {
        this.zhuangmeidanjia = zhuangmeidanjia;
    }

    public String getYun() {
        return yun;
    }

    public void setYun(String yun) {
        this.yun = yun;
    }

    @Override
    public String toString() {
        return "ReWaji{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", carId='" + carId + '\'' +
                ", carType='" + carType + '\'' +
                ", type='" + type + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", oilcarNum='" + oilcarNum + '\'' +
                ", oilL='" + oilL + '\'' +
                ", oilTime='" + oilTime + '\'' +
                ", oilPrice='" + oilPrice + '\'' +
                ", num='" + num + '\'' +
                ", beilv='" + beilv + '\'' +
                ", wuliao='" + wuliao + '\'' +
                ", wajidanjia='" + wajidanjia + '\'' +
                ", shijianxuanze='" + shijianxuanze + '\'' +
                ", jishishijian='" + jishishijian + '\'' +
                ", jishidanjia='" + jishidanjia + '\'' +
                ", zhuangmeiche='" + zhuangmeiche + '\'' +
                ", zhuangmeidanjia='" + zhuangmeidanjia + '\'' +
                ", yun='" + yun + '\'' +
                '}';
    }
}