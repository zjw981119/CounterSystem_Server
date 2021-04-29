package com.gradproject.server.entity;

import lombok.Data;

@Data
public class CumulationCounter {

    private Integer id;

    private String carNum;

    private String counts;

    private String workDay;
}
