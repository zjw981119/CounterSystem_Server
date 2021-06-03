package com.gradproject.server.entity;


import lombok.Data;

@Data
public class MachineDetail {
    // 机械明细表大车字段
    private int id;
    private String carType;
    private String carNo;
    private int tripNum;
    private float unitPrice;// 油的单价
    private float transportDistance;
    private float multiple;
    private float salary;
    private float maintenanceFee;
    private float mealFee;
    private float accessoryFee;
    private float penalty;
    private float reward;
    private float biaoxiang;
    private float sumOilL;

    //需要计算的字段
    private float loadTransportIncome;
    private float biaoxiangCar;
    private float volume;
    private float loadCoalIncome;
    private float grossIncome;
    private float fuelFee;
    private float profit;
    private float oilConsumePerCar;
    private float oilConsumeRatio;

    //挖机字段
    private String material;
    private float timeIncome;

    //查询字段
    private String beginTime;
    private String endTime;
    private String trackType;
    private String trackNo;

}
