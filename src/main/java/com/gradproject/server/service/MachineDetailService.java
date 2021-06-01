package com.gradproject.server.service;


import com.gradproject.server.dao.MachineDetailMapper;
import com.gradproject.server.entity.MachineDetail;
import com.gradproject.server.entity.allCar;
import com.gradproject.server.entity.model.SelfResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Log4j2
public class MachineDetailService {

    @Resource
    private MachineDetailMapper machineDetailMapper;

    public SelfResponse selectMachineDetail(String beginTime, String endTime, String trackType, String trackNo) {

        SelfResponse response=new SelfResponse();
        List<MachineDetail> machineDetails_BigCar;
        List<MachineDetail> machineDetails_Excavator;
        List<MachineDetail> machineDetails_AssistCar;
        String beginTime_Date ;
        String endTime_Date ;
        try {
            machineDetails_BigCar = machineDetailMapper.selectMachineDetail_BIgCar(beginTime,endTime,trackNo);
            if(StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime) ){
                 beginTime_Date = "";
                 endTime_Date = "";
            }else {
                 beginTime_Date = beginTime.substring(0,10);
                 endTime_Date = endTime.substring(0,10);
            }
            machineDetails_Excavator = machineDetailMapper.selectMachineDetail_Excavator(beginTime_Date,endTime_Date,trackNo);
            machineDetails_AssistCar = machineDetailMapper.selectMachineDetail_AssistCar(beginTime_Date,endTime_Date);
            machineDetails_BigCar.addAll(machineDetails_Excavator);
            machineDetails_BigCar.addAll(machineDetails_AssistCar);
        }catch (Exception e){
            log.info("数据库异常：{}，信息：{}", e.getMessage(), e);
            return response.failure("数据库访问异常");
        }
        List<MachineDetail> allSameCarTotal = getSameCarTotal(machineDetails_BigCar);
        log.info("机械明细表查询成功,all车：{}",allSameCarTotal);
        return response.success(allSameCarTotal);
    }

    public SelfResponse selectExcavatorAndCar() {

        SelfResponse response = new SelfResponse();
        allCar allcar = new allCar();
        try {
            allcar.setExcavator(machineDetailMapper.selectExcavator());
            allcar.setCar(machineDetailMapper.selectCar());
        }catch (Exception e){
            log.info("数据库异常：{}，信息：{}", e.getMessage(), e);
            return response.failure("数据库访问异常");
        }
        log.info("挖机：{}，大车：{}");
        return response.success(allcar);

    }
    public List<MachineDetail> getSameCarTotal(List<MachineDetail> allSameCarTotal){
        int flag = 0;
        int tripNumSum = 0;
        float unitPriceSum = 0;

        for(int i=0,j=i+1;i<allSameCarTotal.size()&& j != allSameCarTotal.size();i++,j++){
            if(!allSameCarTotal.get(i).getCarNo().equals(allSameCarTotal.get(j).getCarNo())){
                MachineDetail machineDetail =new MachineDetail();
                for(;flag<j;flag++){
                    tripNumSum += allSameCarTotal.get(flag).getTripNum();
                    unitPriceSum += allSameCarTotal.get(flag).getUnitPrice();
                }
                machineDetail.setCarNo("合计");
                machineDetail.setCarType("-----");
                machineDetail.setTripNum(tripNumSum);
                machineDetail.setUnitPrice(unitPriceSum);
                tripNumSum = 0;
                unitPriceSum = 0;
                allSameCarTotal.add(j,machineDetail);
                i++;
                j++;
                flag = j;
            } else {
                continue;
            }
        }
        return allSameCarTotal;
    }
}
