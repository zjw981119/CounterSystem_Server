package com.gradproject.server.dao;

import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.SelfCounter;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface RfidMapper {
    /*
    当数据库字段名与实体类对应的属性名不一致时，使用@Results映射来将其对应起来。
    column为数据库字段名，porperty为实体类属性名。
    Results注解需要和@Select一起使用
    */
    @Results(id = "RfidResult", value = {
            @Result(property="rfid", column="rfid"),
            @Result(property="carNum", column="car_num"),
            @Result(property="address", column="address")
    })
    @Select("select * from rfid_carnum where rfid = #{rfid} order by id DESC")
    List<RfidCarNum> selectCarByRfid(@Param("rfid") String rfid);

    //@Results注解设置id，然后使用@ResultMap注解来复用这段代码。
    @Select("select * from rfid_carnum where address = #{address} order by id DESC")
    @ResultMap(value ={"RfidResult"})
    List<RfidCarNum> selectRfidByAddress(@Param("address") String address);

    @Select("select * from rfid_carnum where address = #{address} and rfid = #{rfid} order by id DESC")
    @ResultMap(value ={"RfidResult"})
    List<RfidCarNum> selectConfigByAddressAndRfid(@Param("address") String address,@Param("rfid") String rfid);

    //Options注解设置id自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO rfid_carnum(rfid, car_num, address) " +
            "VALUES (#{rfid}, #{carNum}, #{address})")
    int insertConfigData(RfidCarNum record);

    @Update("update rfid_carnum set car_num = #{carNum}, address = #{address} where rfid = #{rfid}")
    int setConfigData(RfidCarNum record);

    @Delete("delete from rfid_carnum where rfid = #{rfid}")
    int deleteConfigData(@Param("rfid") String rfid);
}
