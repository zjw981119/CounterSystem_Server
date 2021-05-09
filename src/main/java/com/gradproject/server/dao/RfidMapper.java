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
    @Select("select * from gn_rfid_carnum where rfid = #{rfid} order by id DESC")
    List<RfidCarNum> selectCarByRfid(@Param("rfid") String rfid);

    //@Results注解设置id，然后使用@ResultMap注解来复用这段代码。
    @Select("select * from gn_rfid_carnum where address = #{address} order by id DESC")
    @ResultMap(value ={"RfidResult"})
    List<RfidCarNum> selectRfidByAddress(@Param("address") String address);

    //动态sql语句，根据address和rfid是否为空来修改SQL
    @Select({"<script>",
    "select rfid, car_num, address from gn_rfid_carnum",
      "<where>",
        "<if test=\"address != null and address != ''\">address = #{address} </if>",
        "<if test=\"rfid != null and rfid != ''\">And rfid = #{rfid} </if>",
      "</where>",
    "</script>"})
    @ResultMap(value ={"RfidResult"})
    List<RfidCarNum> selectRfidByAddressOrRfid(@Param("address") String address,@Param("rfid") String rfid);

    //动态sql语句，根据address和rfid是否为空查询数据总数
    @Select({"<script>",
            "select count(rfid) from gn_rfid_carnum",
            "<where>",
            "<if test=\"address != null and address != ''\">address = #{address} </if>",
            "<if test=\"rfid != null and rfid != ''\">And rfid = #{rfid} </if>",
            "</where>",
            "</script>"})
    Integer CountConfigByAddressOrRfid(@Param("address") String address,@Param("rfid") String rfid);

    //Options注解设置id自增长
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Insert("INSERT INTO gn_rfid_carnum(rfid, car_num, address) " +
            "VALUES (#{rfid}, #{carNum}, #{address})")
    int insertConfigData(RfidCarNum record);

    @Update("update gn_rfid_carnum set car_num = #{carNum}, address = #{address} where rfid = #{rfid}")
    int setConfigData(RfidCarNum record);

    @Delete("delete from gn_rfid_carnum where rfid = #{rfid}")
    int deleteConfigData(@Param("rfid") String rfid);
}
