package com.gradproject.server.entity;

import lombok.Data;

@Data
public class CarConfig {

    private Integer id;

    private String date;

    private String carId;

    private String carType;
    //内外部
    private String type;

    private String bindExcavator;

    private String ownerName;

    private String oilPrice;

    //方量
    private String multiple;

    //标箱量
    private String biaoxiang;

    private Double salary;
    //维修费
    private Double maintenanceFee;

    private Double mealFee;
    //配件费
    private Double accessoryFee;

    private Double penalty;

    private Double reward;


}
