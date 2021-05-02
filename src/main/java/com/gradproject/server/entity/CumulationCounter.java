package com.gradproject.server.entity;

import lombok.Data;

@Data
public class CumulationCounter {

    private Integer id;

    private String carNum;

    private Integer counts;

    private String workDay;
}
