package com.gradproject.server.dao;

import com.gradproject.server.entity.CarConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CarMapper {

    //查询最接近queryTime的配置信息，返回对象列表
    @Select("select * from re_car_config where date = " +
            "(select MAX(date) from re_car_config where date <= #{queryTime}) "+
            "order by car_id ASC")
    List<CarConfig> selectNewestConfig(@Param("queryTime") String queryTime);

    //查询日期为updateTime的配置信息,返回对象列表
    @Select("select * from re_car_config where date = #{updateTime} order by car_id ASC")
    List<CarConfig> SelectConfigByDate(@Param("updateTime") String updateTime);

    //插入车辆配置信息
    //Options注解设置id自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO re_car_config(date, car_id, car_type, type, bind_excavator, owner_name, "+
            "oil_price, multiple, biaoxiang, salary, maintenance_fee, meal_fee, accessory_fee, " +
            "penalty, reward) "+
            "VALUES (#{date}, #{carId}, #{carType}, #{type}, #{bindExcavator}, #{ownerName}, "+
            "#{oilPrice}, #{multiple}, #{biaoxiang}, #{salary}, #{maintenanceFee}, #{mealFee}, #{accessoryFee}, "+
            "#{penalty}, #{reward})")
    int insertCarConfigData(CarConfig record);

    //删除日期为time的所有数据
    @Delete("delete from re_car_config where date = #{updateTime}")
    int deleteConfigByTime(@Param("updateTime") String updateTime);

    //根据id删除配置数据
    @Delete("delete from re_car_config where id = #{id}")
    int deleteConfigByID(@Param("id") Integer id);
}
