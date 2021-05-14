package com.gradproject.server.dao;

import com.gradproject.server.entity.AuxMacConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuxMacMapper {

    //查询最接近queryTime的配置信息修改时间
    @Select("select MAX(date) from re_auxiliarymachinery_config where date <= #{queryTime}")
    String SelectNewestTime(@Param("queryTime") String queryTime);

    //查询日期为updateTime的配置信息,返回对象列表
    @Select("select * from re_auxiliarymachinery_config where date = #{updateTime} order by car_id ASC" )
    List<AuxMacConfig> SelectConfigByDate(@Param("updateTime") String updateTime);

    //插入辅助车辆配置信息
    //Options注解设置id自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO re_auxiliarymachinery_config(date, car_type, car_id, price) "+
            "VALUES (#{date}, #{carType}, #{carId}, #{price})")
    int insertCarConfigData(AuxMacConfig record);

    //删除日期为time的所有数据
    @Delete("delete from re_auxiliarymachinery_config where date = #{updateTime}")
    int deleteConfigByTime(@Param("updateTime") String updateTime);

    //根据id删除配置数据
    @Delete("delete from re_auxiliarymachinery_config where id = #{id}")
    int deleteConfigByID(@Param("id") Integer id);
}
