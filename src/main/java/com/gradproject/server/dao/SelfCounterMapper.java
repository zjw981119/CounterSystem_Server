package com.gradproject.server.dao;

import com.gradproject.server.entity.SelfCounter;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SelfCounterMapper {

    //根据rfid卡号查询该车在工作时间段内的运输次数
    @Select("select count(*) from self_counter where rfid = #{rfid} and time between #{beginTime} and #{endTime}")
    int selectCounts(@Param("rfid") String rfid, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //@Options属性userGeneratedKeys的值为true，并指定实例对象中主键的属性名keyProperty以及在数据库中的字段名keyColumn,主键自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO self_counter(mining_area, car_num, rfid, " +
            "picture, time, sub_counts, total_counts, " +
            "address) " +
            "VALUES (#{miningArea}, #{carNum}, #{rfid}, " +
            "#{picture}, #{time}, #{subCounts}, #{totalCounts}, " +
            "#{address})")
    int insertCounterData(SelfCounter record);

}
