package com.gradproject.server.entity;

import lombok.Data;

@Data
public class CarConfig {

    private Integer id;

    private String carNum;

    private String carType;

    private String place;

    private String grabCar;

    private String ownerName;

    private String initPrice;

    //方量
    private String volume;

    //标箱量
    private String teu;

    private String salary;

    private String repairCost;

    private String foodCost;

    private String fittingsCost;

    private String fine;

    private String bonus;

    private String updateTime;
}
