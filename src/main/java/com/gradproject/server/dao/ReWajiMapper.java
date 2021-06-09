package com.gradproject.server.dao;

import com.gradproject.server.entity.ReWaji;
import com.gradproject.server.entity.ReWajiConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ReWajiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReWaji record);

    ReWaji selectByPrimaryKey(Integer id);

    List<ReWaji> selectAll();

    int updateByPrimaryKey(ReWaji record);

    List<ReWaji> selectByDateAndDiggerNo(@Param("date") String date, @Param("diggerNo")String diggerNo);

    List<ReWajiConfig> selectByDate(String date);

    int deleteByDate(String date);
}