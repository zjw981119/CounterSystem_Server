package com.gradproject.server.dao;

import com.gradproject.server.entity.SelfCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SelfCounterMapper {

    //根据rfid卡号查询该车在工作时间段内的运输次数
    @Select("select count(*) from self_counter where rfid = #{rfid} and time between #{beginTime} and #{endTime}")
    int selectCounts(@Param("rfid") String rfid, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据时间段查询所有矿车的工作记录
    @Select("select * from self_counter where time between #{beginTime} and #{endTime}")
    List<SelfCounter> selectRecordByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据车号和时间段查询矿车的工作记录
    @Select("select * from self_counter where car_num=#{carNum} and time between #{beginTime} and #{endTime} ")
    List<SelfCounter> selectRecordByNumAndTime(@Param("carNum") String carNum, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //@Options属性userGeneratedKeys的值为true，并指定实例对象中主键的属性名keyProperty以及在数据库中的字段名keyColumn,主键自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO self_counter(mining_area, car_num, rfid, " +
            "picture, time, sub_counts, total_counts, " +
            "address) " +
            "VALUES (#{miningArea}, #{carNum}, #{rfid}, " +
            "#{picture}, #{time}, #{subCounts}, #{totalCounts}, " +
            "#{address})")
    int insertCounterData(SelfCounter record);

    @Select("select picture from self_counter where id = #{id}")
    String selectPicById(@Param("id") Integer id);
}
