package com.gradproject.server.dao;

import com.gradproject.server.entity.CumulationCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CumulationCounterMapper {

    @Results(id = "CumulateCounterResult", value = {
            @Result(property="carNum", column="car_num"),
            @Result(property="counts", column="counts"),
            @Result(property="workDay", column="work_day")
    })
    @Select("select * from cumulation_counter where car_num = #{carNum} and work_day = #{workDay}")
    List<CumulationCounter> selectCumulateDataByNumAndDay(@Param("carNum") String carNum, @Param("workDay") String workDay);

    //插入对应车号在对应日期的运输趟数
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO cumulation_counter(car_num, counts, work_day) " +
            "VALUES (#{carNum}, #{counts}, #{workDay})" )
    int insertCumulateData(CumulationCounter record);

    //更新对应车号在对应日期的运输趟数
    @Update("update cumulation_counter set counts = #{counts} "+
            "where car_num = #{carNum} and work_day = #{workDay}")
    int updateCumulateData(CumulationCounter record);
}
