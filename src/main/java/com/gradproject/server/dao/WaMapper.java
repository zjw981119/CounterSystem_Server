package com.gradproject.server.dao;

import com.gradproject.server.entity.Waing;
import org.apache.ibatis.annotations.*;

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
            +"FROM re_waji "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "

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
            +"car_type,car_id,bind_excavator,COUNT(car_id) as tripNum,oil_price, "
            +"multiple,(COUNT(car_id) * oil_price * multiple) as zhuang,(COUNT(car_id) * multiple) as biao,  "
            +"(COUNT(car_id) * multiple * COUNT(car_id) * multiple) as fang, (COUNT(car_id) * oil_price) as mei, ((COUNT(car_id) * oil_price * multiple) + (COUNT(car_id) * oil_price)) as mao "

            +"FROM re_car_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "

            +"and type = '外部' "
            +"</where>"
            +"GROUP BY car_id,bind_excavator  "

            +"</script>"})
    List<Waing> selectW(@Param("value1") String value1, @Param("value2") String value2);

}
