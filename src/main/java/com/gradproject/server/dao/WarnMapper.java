package com.gradproject.server.dao;

import com.gradproject.server.entity.Warning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface WarnMapper {

    @Results(id = "BaseMap", value = {
            @Result(property="car_type", column="car_type"),
            @Result(property="car_no", column="car_no"),
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
            @Result(property="oil_L", column="oil_L")
    })
    @Select({"<script>"
            +"SELECT "
            +"re_car_config.car_type, gn_self_counter.car_no,(count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular))  as tripNum,re_car_config.oil_price, "
            +"gn_self_counter.transport_distance,re_car_config.multiple,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple) as zhuang,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) as biao, "
            +"FORMAT((((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) * ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple)),2) as fang ,re_car_config.jishi,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) as mei,(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) + jishi + ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple)) as mao, "
            +"sum(re_car_config.oil_L) as oil_L,(re_car_config.oil_price * sum(re_car_config.oil_L)) as ran "
            +"FROM gn_self_counter,re_car_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date_format(time,'%Y-%m-%d') <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date_format(time,'%Y-%m-%d') <![CDATA[ <= ]]> #{value2}</if> "
            +"and gn_self_counter.car_no = re_car_config.car_id "
            +"and gn_self_counter.address = 'PANH@001' "
            +"and date_format(time,'%Y-%m-%d') = date  "

            +"and re_car_config.type = '内部' "
            +"and gn_self_counter.transport_distance is not null "
            +"</where>"
            +"GROUP BY gn_self_counter.transport_distance,re_car_config.car_id  "
            +"UNION "
            +"SELECT "
            +"'合计' as car_type, '' as car_no , SUM(q.t),'' as oil_price, "
            +" '' as transport_distance,'' as multiple, format(SUM(q.zhuang),2),format(SUM(q.biao),2), "
            +" format(SUM(q.fang),2),'' AS jishi,SUM(q.mei),SUM(q.mao), "
            +" SUM(q.oil_L),SUM(q.ran) "
            +"FROM "
            +"("
            +"SELECT "
            +"(count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular))  as t, "
            +"re_car_config.oil_price,gn_self_counter.transport_distance,re_car_config.multiple, "
            +"((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple) as zhuang, "
            +"((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) as biao, "
            +"(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple) * ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.multiple)) as fang  , "
            +"re_car_config.jishi,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) as mei, "
            +"(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * re_car_config.oil_price) + jishi + ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  re_car_config.oil_price  * re_car_config.multiple)) as mao, "
            +"sum(re_car_config.oil_L) as oil_L, "
            +"(re_car_config.oil_price * sum(re_car_config.oil_L)) as ran "
            +"FROM gn_self_counter,re_car_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date_format(time,'%Y-%m-%d') <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date_format(time,'%Y-%m-%d') <![CDATA[ <= ]]> #{value2}</if> "
            +"and gn_self_counter.car_no = re_car_config.car_id "
            +"and gn_self_counter.address = 'PANH@001' "

            +"and date_format(time,'%Y-%m-%d') = date  "
            +"and re_car_config.type = '内部' "
            +"and gn_self_counter.transport_distance is not null "
            +"</where>"
            +"GROUP BY gn_self_counter.transport_distance,re_car_config.car_id "
            +" ) q "
            +"UNION "
            +"SELECT "
            +"car_type,car_id,(count(beilv)) as tripNum, oil_price, "
            +" yun,beilv,((count(beilv)) * oil_price * beilv) as zhuang, ((count(beilv)) * beilv) as biao, "
            +" (((count(beilv)) * beilv) * ((count(beilv)) * oil_price * beilv)) as fang, sum(jishishijian*jishidanjia) as jishi,((count(beilv)) * oil_price) as mei,(sum(jishishijian*jishidanjia) + ((count(beilv)) * oil_price) + ((count(beilv)) * oil_price * beilv)) as mao, "
            +" sum(oil_L) as oil_L,(oil_price * sum(oil_L)) as ran "
            +"FROM re_waji "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "
            +"</where>"
            +"GROUP BY car_id,beilv "
            +"UNION "
            +"SELECT "
            +"'合计' as car_type, '' as car_no ,SUM(r.c), '' as oil_price,"
            +"'' as transport_distance,'' as multiple,SUM(r.z),SUM(r.b),"
            +"SUM(r.f),SUM(r.j),SUM(r.m),SUM(r.ma),"
            +"SUM(r.o),SUM(r.e) "
            +"FROM "
            +"("
            +"SELECT "
            +"(count(beilv)) as c,oil_price,"
            +"yun,beilv,((count(beilv)) * oil_price * beilv) as z,((count(beilv)) * beilv) as b,"
            +"(((count(beilv)) * beilv) * ((count(beilv)) * oil_price * beilv)) as f,sum(jishishijian*jishidanjia) as j,((count(beilv)) * oil_price) as m,(sum(jishishijian*jishidanjia) + ((count(beilv)) * oil_price) + ((count(beilv)) * oil_price * beilv)) as ma,"
            +"sum(oil_L) as o,(oil_price * sum(oil_L)) as e "
            +"FROM re_waji "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "
            +"</where>"
            +"GROUP BY car_id,beilv"
            +")r  "
            +"UNION "
            +"SELECT "
            +"car_type,car_id,car_num, price, "
            +"yun,bei,zhuang, biao, "
            +"fang,jishi,mei, mao, "
            +"sum(oil_num),(price*sum(oil_num)) as b  "
            +"FROM re_auxiliarymachinery_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "
            +"</where> "
            +"GROUP BY oil_num,car_id "
            +"UNION "
            +"SELECT "
            +"'合计' as car_type, '' as car_no ,'' as car_num, '' as oil_price, "
            +"'' as transport_distance,'' as multiple,'' as zhuang,'' as biao, "
            +"'' as fang,'' as jishi,'' as mei,'' as mao, "
            +"SUM(t.a),SUM(t.b) "
            +"FROM "
            +"("
            +"SELECT "
            +"car_type,car_id,car_num, price, "
            +"yun,bei,zhuang, biao, "
            +"fang,jishi,mei, mao, "
            +"sum(oil_num) as a ,(price*sum(oil_num)) as b  "
            +"FROM re_auxiliarymachinery_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> #{value2}</if> "
            +"</where> "
            +"GROUP BY oil_num,car_id "
            +")t  "
            +"</script>"})
    List<Warning> selectWarn(@Param("value1") String value1, @Param("value2") String value2);
}
