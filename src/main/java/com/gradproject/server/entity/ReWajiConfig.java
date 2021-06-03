package com.gradproject.server.entity;

import java.io.Serializable;

public class ReWajiConfig implements Serializable {
    private Integer id;

    private String date;

    private String carId;

    private String carType;

    private String type;

    private String ownerName;

    private Double ratio;

    private String oilPrice;

    private Double salary;

    private Double maintenanceFee;

    private Double mealFee;

    private Double accessoryFee;

    private Double penalty;

    private Double reward;

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

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(Double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public Double getMealFee() {
        return mealFee;
    }

    public void setMealFee(Double mealFee) {
        this.mealFee = mealFee;
    }

    public Double getAccessoryFee() {
        return accessoryFee;
    }

    public void setAccessoryFee(Double accessoryFee) {
        this.accessoryFee = accessoryFee;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "ReWajiConfig{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", carId='" + carId + '\'' +
                ", carType='" + carType + '\'' +
                ", type='" + type + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ratio='" + ratio + '\'' +
                ", oilPrice='" + oilPrice + '\'' +
                ", salary=" + salary +
                ", maintenanceFee=" + maintenanceFee +
                ", mealFee=" + mealFee +
                ", accessoryFee=" + accessoryFee +
                ", penalty=" + penalty +
                ", reward=" + reward +
                '}';
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

}