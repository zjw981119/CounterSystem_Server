package com.gradproject.server.dao;

import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.SelfCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SelfCounterMapper {

    //根据rfid卡号查询该车在工作时间段内的运输次数
    @Select("select count(*) from gn_self_counter where card_no = #{rfid} and time between #{beginTime} and #{endTime}")
    int selectCounts(@Param("rfid") String rfid, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据时间段查询所有矿车的工作记录
    @Select("select * from gn_self_counter where time between #{beginTime} and #{endTime}")
    List<SelfCounter> selectRecordByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据车号和时间段动态查询矿车的工作记录
    @Results(id = "CarCounterResult", value = {
            @Result(property="address", column="address"),
            @Result(property="grabCarNum", column="grab_car_no"),
            @Result(property="carNum", column="car_no"),
            @Result(property="rfid", column="card_no"),
            @Result(property="time", column="time"),
            @Result(property="degree", column="degree"),
            @Result(property="material", column="material"),
            @Result(property="distance", column="distance"),
            @Result(property="price", column="price"),
            @Result(property="isFull", column="is_full"),
            @Result(property="remark", column="remark"),
            @Result(property="picture", column="picture")
    })
    @Select({"<script>",
            "select * from gn_self_counter",
            "<where>",
            "<if test=\"beginTime != '' and endTime != ''\">time between #{beginTime} and #{endTime} </if>",
            "<if test=\"carNum != null and carNum != ''\">And car_no = #{carNum} </if>",
            "</where>",
            "</script>"})
    List<SelfCounter> selectRecordByNumOrTime(@Param("carNum") String carNum, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据车号和时间段动态查询矿车的工作记录数量
    @Select({"<script>",
            "select count(*) from gn_self_counter",
            "<where>",
            "<if test=\"beginTime != '' and endTime != ''\">time between #{beginTime} and #{endTime} </if>",
            "<if test=\"carNum != null and carNum != ''\">And car_no = #{carNum} </if>",
            "</where>",
            "</script>"})
    int CountRecordByDynamic(@Param("beginTime") String beginTime, @Param("endTime") String endTime,@Param("carNum") String carNum);
    //@ResultMap(value ={"CarCounterResult"})

    //@Options属性userGeneratedKeys的值为true，并指定实例对象中主键的属性名keyProperty以及在数据库中的字段名keyColumn,主键自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO gn_self_counter(mining_area, car_no, card_no, " +
            "picture, time, degree, address) " +
            "VALUES (#{miningArea}, #{carNum}, #{rfid}, " +
            "#{picture}, #{time}, #{degree}, #{address})")
    int insertCounterData(SelfCounter record);

    @Select("select picture from gn_self_counter where id = #{id}")
    String selectPicById(@Param("id") Integer id);

    //修改车载情况
    @Update("update gn_self_counter set car_load = #{carLoad} where id = #{id}")
    int updateCarloadDataById(@Param("carLoad") String carLoad,@Param("id") Integer id);
}
