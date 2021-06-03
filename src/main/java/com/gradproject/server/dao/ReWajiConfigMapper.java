package com.gradproject.server.dao;

import com.gradproject.server.entity.DiggerConfig;
import com.gradproject.server.entity.ReWajiConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ReWajiConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReWajiConfig record);

    ReWajiConfig selectByPrimaryKey(Integer id);

    List<ReWajiConfig> selectAll();

    int updateByPrimaryKey(ReWajiConfig record);

    List<ReWajiConfig> selectByDate(String date);

    int deleteByDate(@Param("date") String date);
}