package com.gradproject.server.dao;

import com.gradproject.server.entity.Waing;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface WaMapper {

    @Results(id = "BaseMap", value = {
            @Result(property="car_type", column="car_type"),
            @Result(property="car_no", column="car_id"),
            @Result(property="tripNum", column="tripNum"),
            @Result(property="oil_price", column="oil_price"),

            @Result(property="multiple", column="beilv"),
            @Result(property="salary", column="salary"),
            @Result(property="maintenanceFee", column="maintenance_fee"),
            @Result(property="zhuang", column="zhuang"),
            @Result(property="biao", column="biao"),
            @Result(property="fang", column="fang"),
            @Result(property="reward", column="reward"),
            @Result(property="biaoxiang", column="biaoxiang"),
            @Result(property="mei", column="mei"),
            @Result(property="jishi", column="jishi"),
            @Result(property="mao", column="mao"),
            @Result(property="ran", column="ran"),
            @Result(property="oil_L", column="oil_L"),
            @Result(property="bind_excavator", column="bind_excavator")
    })
    @Select({"<script>"
            +"SELECT "
            +"car_type,car_id,COUNT(car_id ) as tripNum, oil_price,   "
            +"beilv,((count(car_id)) * oil_price * beilv) as zhuang, ((count(car_id)) * beilv) as biao, "
            +" (((count(car_id)) * beilv) * ((count(car_id)) * oil_price * beilv)) as fang, sum(jishishijian*jishidanjia) as jishi,((count(car_id)) * oil_price) as mei,(sum(jishishijian*jishidanjia) + ((count(car_id)) * oil_price) + ((count(car_id)) * oil_price * beilv)) as mao, "
            +" sum(oil_L) as oil_L,(oil_price * sum(oil_L)) as ran "
            +"FROM re_waji_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d')</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d')</if> "

            +"and type = '外部' "
            +"</where>"
            +"GROUP BY car_id  "

            +"</script>"})

    List<Waing> selectWa(@Param("value1") String value1, @Param("value2") String value2);

    @Results(id = "BaseMap2", value = {
            @Result(property="car_type", column="car_type"),
            @Result(property="car_no", column="car_id"),
            @Result(property="tripNum", column="tripNum"),
            @Result(property="oil_price", column="oil_price"),
            @Result(property="transport_distance", column="transport_distance"),
            @Result(property="multiple", column="multiple"),
            @Result(property="salary", column="salary"),
            @Result(property="maintenanceFee", column="maintenance_fee"),
            @Result(property="zhuang", column="zhuang"),
            @Result(property="biao", column="biao"),
            @Result(property="fang", column="fang"),
            @Result(property="reward", column="reward"),
            @Result(property="biaoxiang", column="biaoxiang"),
            @Result(property="mei", column="mei"),
            @Result(property="jishi", column="jishi"),
            @Result(property="mao", column="mao"),
            @Result(property="ran", column="ran"),
            @Result(property="oil_L", column="oil_L"),
            @Result(property="bind_excavator", column="bind_excavator")
    })
    @Select({"<script>"
            +"SELECT "
            +"re_car_config.car_type, re_car_config.car_id,(count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular))  as tripNum,re_car_config.oil_price, "
            +"gn_self_counter.transport_distance,re_car_config.multiple,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple) as zhuang,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) as biao, "
            +"FORMAT((((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) * ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple)),2) as fang ,re_car_config.jishi,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) as mei,(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) + jishi + ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple)) as mao, "
            +"sum(re_car_config.oil_L) as oil_L,(re_car_config.oil_price * sum(re_car_config.oil_L)) as ran,re_car_config.bind_excavator  "

            +"FROM gn_self_counter,re_car_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and time <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and time <![CDATA[ <= ]]> #{value2}</if> "
            +"and gn_self_counter.car_no = re_car_config.car_id "
            +"and gn_self_counter.address = 'PANH@001' "
            +"and date_format(time,'%Y-%m-%d') = date  "

            +"and re_car_config.type = '外部' "
            +"and gn_self_counter.transport_distance is not null "
            +"</where>"
            +"GROUP BY gn_self_counter.transport_distance,re_car_config.car_id ,re_car_config.bind_excavator  "

            +"</script>"})
    List<Waing> selectW(@Param("value1") String value1, @Param("value2") String value2);

}
