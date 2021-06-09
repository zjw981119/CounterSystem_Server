package com.gradproject.server.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Result;

@Data
public class Warning {
    private  Integer id;
    private  String tripNum;
    private  String oil_price;
    private  String car_type;
    private  String car_no;
    private  String value1;
    private  String  value2;
    private  String isConfirm;
    private  String transport_distance;
    private  String multiple;
    private  String zhuang;
    private  String biao;
    private  String mao;
    private  String mei;
    private  String jishi;
    private  String oil_L;
    private  String ran;
    private  String fang;


}
