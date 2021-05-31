package com.gradproject.server.entity;

import lombok.Data;

@Data
public class SelfCounter {

    private Integer id;

    private String miningArea;

    //车号
    private String carNum;
    //rfid号
    private String rfid;
    //挖掘机号
    private String grabCarNum;

    private String picture;

    private String time;
    //当日运输次数
    private String degree;

    //private String totalCounts;

    //计数宝名称
    private String address;

    //满载情况
    private String isFull;

    //物料
    private String material;

    //运距
    private String distance;

    //单价
    private String price;

    //特殊情况加车数
    private String additionalCount;

}
