package com.gradproject.server.dao;

import com.gradproject.server.entity.DiggerConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiggerConfigMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DiggerConfig record);

    DiggerConfig selectByPrimaryKey(Integer id);

    List<DiggerConfig> selectAll();

    int updateByPrimaryKey(DiggerConfig record);

//    @Results(id = "diggerConfigMapping",value = {
//            @Result(property = "id",column = "id"),
//            @Result(property = "date",column = "date"),
//            @Result(property = "carId",column = "car_id"),
//            @Result(property = "carType",column = "car_type"),
//            @Result(property = "type",column = "type"),
//            @Result(property = "ownerName",column = "owner_name"),
//            @Result(property = "oilPrice",column = "oil_price"),
//            @Result(property = "salary",column = "salary"),
//            @Result(property = "maintenanceFee",column = "maintenance_fee"),
//            @Result(property = "mealFee",column = "meal_fee"),
//            @Result(property = "accessoryFee",column = "accessory_fee"),
//            @Result(property = "penalty",column = "penalty"),
//            @Result(property = "reward",column = "reward")
//    })
//    @Select("select * from re_waji_config where date=#{date}")
    List<DiggerConfig> selectByDate(String date);

}