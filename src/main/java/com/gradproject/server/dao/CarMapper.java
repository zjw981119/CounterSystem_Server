package com.gradproject.server.dao;

import com.gradproject.server.entity.CarConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CarMapper {

    //查询日期为updateTime的配置信息,返回对象列表
    @Select("select * from gn_car_config where update_time = #{updateTime}")
    List<CarConfig> SelectConfigByDate(@Param("updateTime") String updateTime);

    //插入车辆配置信息
    //Options注解设置id自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO gn_car_config(car_num, car_type, place, grab_car, ownername, "+
            "init_price, volume, teu, salary, repair_cost, food_cost, fittings_cost, " +
            "fine, bonus, update_time) "+
            "VALUES (#{carNum}, #{carType}, #{place}, #{grabCar}, #{ownerName}, "+
            "#{initPrice}, #{volume}, #{teu}, #{salary}, #{repairCost}, #{foodCost}, #{fittingsCost}, "+
            "#{fine}, #{bonus}, #{updateTime})")
    int insertCarConfigData(CarConfig record);

    //删除日期为time的所有数据
    @Delete("delete from gn_car_config where update_time = #{updateTime}")
    int deleteConfigByTime(@Param("updateTime") String updateTime);

    //根据id删除配置数据
    @Delete("delete from gn_car_config where id = #{id}")
    int deleteConfigByID(@Param("id") Integer id);
}
