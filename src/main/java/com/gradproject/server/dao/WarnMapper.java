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
            +"C.car_type, gn_self_counter.car_no,(count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular))  as tripNum,C.oil_price, "
            +"gn_self_counter.transport_distance,C.multiple,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.oil_price  * C.multiple) as zhuang,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.multiple) as biao, "
            +"FORMAT((((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.multiple) * ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.multiple)),2) as fang ,C.jishi,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.oil_price) as mei,(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.oil_price) + jishi + ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.oil_price  * C.multiple)) as mao, "
            +"sum(C.oil_L) as oil_L,(C.oil_price * sum(C.oil_L)) as ran "
            +"FROM gn_self_counter, "
            +"(SELECT A.* FROM re_car_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_car_config WHERE date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d') AND date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d') GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate  and A.type = '内部') AS C "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and time <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and time <![CDATA[ <= ]]> #{value2}</if> "
            +"and gn_self_counter.car_no = C.car_id "
            +"and gn_self_counter.address = 'PANH@001' "


            +"and gn_self_counter.transport_distance is not null "
            +"</where>"
            +"GROUP BY gn_self_counter.transport_distance,C.car_id  "
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
            +"C.oil_price, "
            +"gn_self_counter.transport_distance,C.multiple,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.oil_price  * C.multiple) as zhuang,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.multiple) as biao, "
            +"FORMAT((((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.multiple) * ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.multiple)),2) as fang ,C.jishi,((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.oil_price) as mei,(((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) * C.oil_price) + jishi + ((count(gn_self_counter.transport_distance)+SUM(gn_self_counter.addcar_particular)) *  C.oil_price  * C.multiple)) as mao, "
            +"sum(C.oil_L) as oil_L,(C.oil_price * sum(C.oil_L)) as ran "
            +"FROM gn_self_counter, "
            +"(SELECT A.* FROM re_car_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_car_config WHERE date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d') AND date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d') GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate  and A.type = '内部') AS C "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and time <![CDATA[ >= ]]> #{value1}</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and time <![CDATA[ <= ]]> #{value2}</if> "
            +"and gn_self_counter.car_no = C.car_id "
            +"and gn_self_counter.address = 'PANH@001' "


            +"and gn_self_counter.transport_distance is not null "
            +"</where>"
            +"GROUP BY gn_self_counter.transport_distance,C.car_id  "
            +" ) q "
            +"UNION "
            +"SELECT "
            +"C.car_type,C.car_id,(sum(num )) as tripNum, C.oil_price, "
            +" yun,C.beilv,((sum(num )) * C.oil_price * C.beilv) as zhuang, ((sum(num )) * C.beilv) as biao, "
            +" (((sum(num )) * C.beilv) * ((sum(num )) * C.oil_price * C.beilv)) as fang, sum(re_waji.jishishijian*re_waji.jishidanjia) as jishi,((sum(num )) * C.oil_price) as mei,(sum(re_waji.jishishijian*re_waji.jishidanjia) + ((sum(num )) * C.oil_price) + ((sum(num )) * C.oil_price * C.beilv)) as mao, "
            +" sum(re_waji.oil_L) as oil_L,(C.oil_price * sum(re_waji.oil_L)) as ran "
            +"FROM re_waji , "
            +"(SELECT A.* FROM re_waji_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_waji_config WHERE date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d') AND date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d') GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate and A.type = '内部' ) AS C "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and re_waji.date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d')</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and re_waji.date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d')</if> "
            +"and C.car_id = re_waji.car_id "



            +"</where>"
            +"GROUP BY C.car_id "
            +"UNION "
            +"SELECT "
            +"'合计' as car_type, '' as car_no ,SUM(r.c), '' as oil_price,"
            +"'' as transport_distance,'' as multiple,SUM(r.z),SUM(r.b),"
            +"SUM(r.f),SUM(r.j),SUM(r.m),SUM(r.ma),"
            +"SUM(r.o),SUM(r.e) "
            +"FROM "
            +"("
            +"SELECT "
            +"(sum(num )) as c,C.oil_price,"
            +"yun,C.beilv,((sum(num )) * C.oil_price * C.beilv) as z,((sum(num )) * C.beilv) as b,"
            +"(((sum(num )) * C.beilv) * ((sum(num )) * C.oil_price * C.beilv)) as f,sum(re_waji.jishishijian*re_waji.jishidanjia) as j,((sum(num )) *C.oil_price) as m,(sum(re_waji.jishishijian*re_waji.jishidanjia) + ((sum(num )) *C.oil_price) + ((sum(num )) * C.oil_price * C.beilv)) as ma,"
            +"sum(re_waji.oil_L) as o,(C.oil_price * sum(re_waji.oil_L)) as e "
            +"FROM re_waji , "
            +"(SELECT A.* FROM re_waji_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_waji_config WHERE date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d') AND date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d') GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate and A.type = '内部' ) AS C "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and re_waji.date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d')</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and re_waji.date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d')</if> "
            +"and C.car_id = re_waji.car_id "
            +"</where>"
            +"GROUP BY C.car_id "
            +")r  "
            +"UNION "
            +"SELECT "
            +"car_type,car_id,car_num, price, "
            +"yun,bei,zhuang, biao, "
            +"fang,jishi,mei, mao, "
            +"sum(oil_num),(price*sum(oil_num)) as b  "
            +"FROM re_auxiliarymachinery_config "
            +"<where>"
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d')</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d')</if> "
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
            +"<if test=\"value1 !=null and value1 !=''\"> and date <![CDATA[ >= ]]> DATE_FORMAT(#{value1},'%Y-%m-%d')</if>"
            +"<if test=\"value2 !=null and value2 !=''\"> and date <![CDATA[ <= ]]> DATE_FORMAT(#{value2},'%Y-%m-%d')</if> "
            +"</where> "
            +"GROUP BY oil_num,car_id "
            +")t  "
            +"</script>"})
    List<Warning> selectWarn(@Param("value1") String value1, @Param("value2") String value2);
}
