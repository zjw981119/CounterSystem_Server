package com.gradproject.server.entity;

import lombok.Data;

@Data
public class CarRecordSelection {

    //车号数组
    private String[] carnumSelection;

    //刷卡器数组
    private String[] addressSelection;

    //挖机数组
    private String[] diggerSelection;

}
