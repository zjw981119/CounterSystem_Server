package com.gradproject.server.dao;

import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.SelfCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SelfCounterMapper {

    //查询在某一时间段内所有记录的不重复车号
    @Select("select distinct car_no from gn_self_counter where time between #{beginTime} and #{endTime} ORDER BY car_no ASC" )
    String[] getcarnumSetlection(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //查询某一时间段内所有记录的不重复刷卡器地址
    @Select("select distinct address from gn_self_counter where time between #{beginTime} and #{endTime} ORDER BY address ASC" )
    String[] getaddressSetlection(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //查询某一时间段内所有记录的不重复挖机地址
    @Select("select distinct grab_car_no from gn_self_counter where time between #{beginTime} and #{endTime} ORDER BY grab_car_no ASC" )
    String[] getdiggerSetlection(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

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
            @Result(property="additionalCount", column="additional_count"),
            @Result(property="remark", column="remark"),
            @Result(property="picture", column="picture")
    })
    @Select({"<script>",
            "select * from gn_self_counter",
            "<where>",
            "<if test=\"beginTime != '' and endTime != ''\">time between #{beginTime} and #{endTime} </if>",
            "<if test=\"carNum != null and carNum != ''\">And car_no = #{carNum} </if>",
            "<if test=\"address != null and address != ''\">And address = #{address} </if>",
            "<if test=\"grabCarNum != null and grabCarNum != ''\">And grab_car_no = #{grabCarNum} </if>",
            "</where>",
            "</script>"})
    List<SelfCounter> selectRecordByNumOrTime(@Param("carNum") String carNum, @Param("address") String address, @Param("grabCarNum") String grabCarNum,
                                              @Param("beginTime") String beginTime, @Param("endTime") String endTime);

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

    //修改数据(车载、物料、运距、价格、加车数)
    @Update("update gn_self_counter set is_full = #{isFull}, material = #{materail}, " +
    "distance = #{distance}, price = #{price}, additional_count = #{additionalCount} " +
    "where id = #{id}")
    int updateDataById(@Param("isFull") String isFull,@Param("materail") String materail,
                       @Param("distance") String distance,@Param("price") String price,
                       @Param("additionalCount") String additionalCount,@Param("id") Integer id);
}
