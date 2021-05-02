package com.gradproject.server.dao;

import com.gradproject.server.entity.CumulationCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CumulationCounterMapper {

    @Results(id = "CumulateCounterResult", value = {
            @Result(property="id", column="id"),
            @Result(property="carNum", column="car_num"),
            @Result(property="counts", column="counts"),
            @Result(property="workDay", column="work_day")
    })
    @Select("select * from cumulation_counter where car_num = #{carNum} and work_day = #{workDay}")
    List<CumulationCounter> selectCumulateDataByNumAndDay(@Param("carNum") String carNum, @Param("workDay") String workDay);

    //#{beginTime} AND #{endTime}
    //根据时间段统计所有车号对应的运输总趟数，用group by对车号分类。
    @Select("select id, car_num, SUM(cumulation_counter.counts) AS counts from cumulation_counter "+
            "where work_day BETWEEN #{beginTime} AND #{endTime} " +
            "GROUP BY car_num")
    @ResultMap(value ={"CumulateCounterResult"})
    //@Param("beginTime") String beginTime, @Param("endTime") String endTime
    List<CumulationCounter> selectCarnumCounts(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

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
