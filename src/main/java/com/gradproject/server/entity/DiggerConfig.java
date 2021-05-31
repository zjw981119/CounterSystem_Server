package com.gradproject.server.entity;

import lombok.Data;

@Data
public class DiggerConfig {
    private Integer id;
    private String date;
    private String carId;
    private String carType;
    private String type;
    private String ownerName;
    private String oilPrice;
    private Double salary;
    private Double maintenanceFee;
    private Double mealFee;
    private Double accessoryFee;
    private Double penalty;
    private Double reward;
}
