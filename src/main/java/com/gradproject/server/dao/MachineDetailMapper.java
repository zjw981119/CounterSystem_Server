package com.gradproject.server.dao;


import com.gradproject.server.entity.MachineDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MachineDetailMapper {

    @Results(id = "BaseMap", value = {
            @Result(property="id", column="id"),
            @Result(property="carType", column="car_type"),
            @Result(property="carNo", column="car_no"),
            @Result(property="tripNum", column="trip_num"),
            @Result(property="unitPrice", column="unitprice"),
            @Result(property="transportDistance", column="transport_distance"),
            @Result(property="multiple", column="multiple"),
            @Result(property="salary", column="salary"),
            @Result(property="maintenanceFee", column="maintenance_fee"),
            @Result(property="mealFee", column="meal_fee"),
            @Result(property="accessoryFee", column="accessory_fee"),
            @Result(property="penalty", column="penalty"),
            @Result(property="reward", column="reward"),
            @Result(property="biaoxiang", column="biaoxiang"),
            @Result(property="sumOilL", column="sum_oil_L"),
            @Result(property="material", column="wuliao"),
            @Result(property="timeIncome", column="time_income")
    })
    @Select("<script>"
            +"SELECT"
            +" gn_self_counter.id,C.car_type,gn_self_counter.car_no,count(*) as trip_num,gn_self_counter.unitprice,"
            +"gn_self_counter.transport_distance,C.multiple,C.salary,C.maintenance_fee,"
            +"C.meal_fee,C.accessory_fee,C.penalty,C.reward,"
            +"C.biaoxiang,sum(C.oil_L) as sum_oil_L"
            +" FROM gn_self_counter,"//****注意,****
            +"(SELECT A.* FROM re_car_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_car_config WHERE date &gt;= #{beginTime} AND date &lt;= #{endTime} GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate) AS C "
            +"<where>"
            +"<if test=\"beginTime !=null and beginTime !=''\">gn_self_counter.time &gt;= #{beginTime} </if>"
            +"<if test=\"endTime !=null and endTime !=''\">and gn_self_counter.time &lt;= #{endTime} </if>"
            +"<if test=\"trackNo !=null and trackNo !=''\">and gn_self_counter.car_no = #{trackNo} </if>"
            +"and gn_self_counter.car_no = C.car_id"
            +" and gn_self_counter.address = 'PANH@001'"
            +"</where>"
            +"GROUP BY gn_self_counter.car_no,gn_self_counter.unitprice"
            +"</script>")
    List<MachineDetail> selectMachineDetail_BIgCar(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("trackNo") String s);
    @Select("<script>"
            +"SELECT"
            +" re_waji.id,C.car_type,re_waji.car_id as car_no,count(*) as trip_num,C.oil_price as unitprice,re_waji.wuliao,"
            +"re_waji.beilv as multiple,re_waji.jishishijian*re_waji.jishidanjia as time_income,C.salary,C.maintenance_fee,"
            +"C.meal_fee,C.accessory_fee,C.penalty,"
            +"C.reward,sum(re_waji.oil_L) as sum_oil_L"
            +" FROM re_waji,"//****注意,****
            +"(SELECT A.* FROM re_waji_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_waji_config WHERE date &gt;= #{beginTime_Date} AND date &lt;= #{endTime_Date} GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate) AS C "
            +"<where>"
            +"<if test=\"beginTime_Date !=null and beginTime_Date !=''\">re_waji.date &gt;= #{beginTime_Date} </if>"
            +"<if test=\"endTime_Date !=null and endTime_Date !=''\">and re_waji.date &lt;= #{endTime_Date} </if>"
            +"<if test=\"trackNo !=null and trackNo !=''\">and re_waji.car_id = #{trackNo} </if>"
            +"and re_waji.car_id = C.car_id"
            +"</where>"
            +"GROUP BY re_waji.car_id,re_waji.wuliao"
            +"</script>")
    @ResultMap(value ={"BaseMap"})
    List<MachineDetail> selectMachineDetail_Excavator(@Param("beginTime_Date") String beginTime_Date,@Param("endTime_Date") String endTime_Date,@Param("trackNo") String s);

    @Select("<script>"
            +"SELECT"
            +" C.id,C.car_type,C.car_id as car_no,"
            +"C.price as unitprice,C.oil_num as sum_oil_L"
            +" FROM "//****注意空格****
            +"(SELECT A.* FROM re_auxiliarymachinery_config as A,(SELECT car_id,MAX(date) as maxdate FROM re_auxiliarymachinery_config WHERE date &gt;= #{beginTime_Date} AND date &lt;= #{endTime_Date} GROUP BY car_id) as B "
            +"WHERE A.car_id = B.car_id AND A.date = B.maxdate) AS C "
            +"ORDER BY C.car_id ASC"
            +"</script>")
    @ResultMap(value ={"BaseMap"})
    List<MachineDetail> selectMachineDetail_AssistCar(@Param("beginTime_Date") String beginTime_Date,@Param("endTime_Date") String endTime_Date);

    @Select("select DISTINCT car_id from re_waji_config order by car_id ASC")
    List<String> selectExcavator();

    @Select("select DISTINCT car_id from re_car_config order by car_id ASC")
    List<String> selectCar();
}
