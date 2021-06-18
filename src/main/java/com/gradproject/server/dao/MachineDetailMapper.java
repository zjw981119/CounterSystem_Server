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
            +" gn_self_counter.id,re_car_config.car_type,gn_self_counter.car_no,count(*) as trip_num,gn_self_counter.unitprice,"
            +"gn_self_counter.transport_distance,re_car_config.multiple,re_car_config.salary,re_car_config.maintenance_fee,"
            +"re_car_config.meal_fee,re_car_config.accessory_fee,re_car_config.penalty,re_car_config.reward,"
            +"re_car_config.biaoxiang,sum(re_car_config.oil_L) as sum_oil_L"
            +" FROM gn_self_counter,re_car_config "//****注意空格****
            +"<where>"
            +"<if test=\"beginTime !=null and beginTime !=''\">gn_self_counter.time &gt;= #{beginTime} </if>"
            +"<if test=\"endTime !=null and endTime !=''\">and gn_self_counter.time &lt;= #{endTime} </if>"
            +"<if test=\"trackNo !=null and trackNo !=''\">and gn_self_counter.car_no = #{trackNo} </if>"
            +"and gn_self_counter.car_no = re_car_config.car_id"
            +" and gn_self_counter.address = 'PANH@001'"
            +"</where>"
            +"GROUP BY gn_self_counter.car_no,gn_self_counter.unitprice"
            +"</script>")
    List<MachineDetail> selectMachineDetail_BIgCar(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("trackNo") String s);
    @Select("<script>"
            +"SELECT"
            +" re_waji.id,re_waji_config.car_type,re_waji.car_id as car_no,count(*) as trip_num,re_waji_config.oil_price as unitprice,re_waji.wuliao,"
            +"re_waji.beilv as multiple,re_waji.jishishijian*re_waji.jishidanjia as time_income,re_waji_config.salary,re_waji_config.maintenance_fee,"
            +"re_waji_config.meal_fee,re_waji_config.accessory_fee,re_waji_config.penalty,"
            +"re_waji_config.reward,sum(re_waji.oil_L) as sum_oil_L"
            +" FROM re_waji,re_waji_config "//****注意空格****
            +"<where>"
            +"<if test=\"beginTime_Date !=null and beginTime_Date !=''\">re_waji.date &gt;= #{beginTime_Date} </if>"
            +"<if test=\"endTime_Date !=null and endTime_Date !=''\">and re_waji.date &lt;= #{endTime_Date} </if>"
            +"<if test=\"trackNo !=null and trackNo !=''\">and re_waji.car_id = #{trackNo} </if>"
            +"and re_waji.car_id = re_waji_config.car_id"
            +"</where>"
            +"GROUP BY re_waji.car_id,re_waji.wuliao"
            +"</script>")
    @ResultMap(value ={"BaseMap"})
    List<MachineDetail> selectMachineDetail_Excavator(@Param("beginTime_Date") String beginTime_Date,@Param("endTime_Date") String endTime_Date,@Param("trackNo") String s);

    @Select("<script>"
            +"SELECT"
            +" id,car_type,car_id as car_no,"
            +"price as unitprice,oil_num as sum_oil_L"
            +" FROM re_auxiliarymachinery_price_config "//****注意空格****
            +"<where>"
            +"date = (select max(date) from re_auxiliarymachinery_price_config)"
            +"</where>"
            +"ORDER BY car_id ASC"
            +"</script>")
    @ResultMap(value ={"BaseMap"})
    List<MachineDetail> selectMachineDetail_AssistCar(@Param("beginTime_Date") String beginTime_Date,@Param("endTime_Date") String endTime_Date);

    @Select("select car_id from re_waji_config")
    List<String> selectExcavator();

    @Select("select car_id from re_car_config")
    List<String> selectCar();
}
