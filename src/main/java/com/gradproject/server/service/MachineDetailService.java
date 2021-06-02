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
        }catch (Exception e){
            log.info("数据库异常：{}，信息：{}", e.getMessage(), e);
            return response.failure("数据库访问异常");
        }
        List<MachineDetail> allCalculation = getCalculation(machineDetails_BigCar);
        List<MachineDetail> allSameCarTotal = getSameCarTotal(allCalculation);
        //辅助车辆先不合计
        allSameCarTotal.addAll(machineDetails_AssistCar);
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
    //计算其他字段的值
    public List<MachineDetail> getCalculation(List<MachineDetail> allCarCalculation){
        for(MachineDetail car : allCarCalculation){
            car.setLoadTransportIncome(car.getTripNum()*car.getMultiple()*car.getUnitPrice());
            car.setBiaoxiangCar(car.getMultiple()*car.getTripNum());
            car.setVolume(car.getBiaoxiangCar()*car.getBiaoxiang());
            car.setLoadCoalIncome(car.getTripNum()*car.getUnitPrice());
            car.setGrossIncome(car.getLoadTransportIncome()+car.getTimeIncome()+car.getLoadCoalIncome());
            car.setFuelFee(car.getSumOilL()*car.getUnitPrice());
            car.setProfit(car.getGrossIncome()-car.getFuelFee()-car.getSalary()-car.getMaintenanceFee()-car.getMealFee()
            -car.getAccessoryFee()-car.getPenalty()+car.getReward());
            car.setOilConsumePerCar(car.getSumOilL()/car.getTripNum());
            car.setOilConsumeRatio(car.getFuelFee()/car.getProfit());

        }
        return allCarCalculation;

    }
    //连续的相同车号的合计
    public List<MachineDetail> getSameCarTotal(List<MachineDetail> allSameCarTotal){
        int flag = 0;//永远指向同一车号的第一个
        int tripNumSum = 0;
        float transportDistanceSum = 0;
        float loadTransportIncomeSum = 0;
        float biaoxiangCarSum = 0;
        float volumeSum = 0;
        float loadCoalIncomeSum = 0;
        float grossIncomeSum = 0;
        float fuelFeeSum = 0;
        float salarySum = 0,maintenanceFeeSum = 0,mealFeeSum = 0,accessoryFeeSum = 0,penaltySum = 0,rewardSum = 0;
        float profitSum = 0;
        //注意最后一次合计的情况
        for(int i=0,j=i+1;i<allSameCarTotal.size()&& j <=allSameCarTotal.size();i++,j++){
            if(j==allSameCarTotal.size() ? true : !allSameCarTotal.get(i).getCarNo().equals(allSameCarTotal.get(j).getCarNo())){
                MachineDetail machineDetail =new MachineDetail();
                //合计相同车号的对应字段
                for(;flag<j;flag++){
                    tripNumSum += allSameCarTotal.get(flag).getTripNum();
                    transportDistanceSum += allSameCarTotal.get(flag).getTransportDistance();
                    loadTransportIncomeSum += allSameCarTotal.get(flag).getLoadTransportIncome();
                    biaoxiangCarSum += allSameCarTotal.get(flag).getBiaoxiangCar();
                    volumeSum += allSameCarTotal.get(flag).getVolume();
                    loadCoalIncomeSum+= allSameCarTotal.get(flag).getLoadCoalIncome();
                    grossIncomeSum += allSameCarTotal.get(flag).getGrossIncome();
                    fuelFeeSum += allSameCarTotal.get(flag).getFuelFee();
                    salarySum += allSameCarTotal.get(flag).getSalary();
                    maintenanceFeeSum += allSameCarTotal.get(flag).getMaintenanceFee();
                    mealFeeSum += allSameCarTotal.get(flag).getMealFee();
                    accessoryFeeSum += allSameCarTotal.get(flag).getAccessoryFee();
                    penaltySum += allSameCarTotal.get(flag).getPenalty();
                    rewardSum += allSameCarTotal.get(flag).getReward();
                    profitSum += allSameCarTotal.get(flag).getProfit();
                }
                //添加合计
                machineDetail.setCarNo("合计");
                machineDetail.setCarType("-----");
                machineDetail.setTripNum(tripNumSum);
                machineDetail.setTransportDistance(transportDistanceSum);
                machineDetail.setLoadTransportIncome(loadTransportIncomeSum);
                machineDetail.setBiaoxiangCar(biaoxiangCarSum);
                machineDetail.setVolume(volumeSum);
                machineDetail.setLoadCoalIncome(loadCoalIncomeSum);
                machineDetail.setGrossIncome(grossIncomeSum);
                machineDetail.setFuelFee(fuelFeeSum);
                machineDetail.setSalary(salarySum);
                machineDetail.setMaintenanceFee(maintenanceFeeSum);
                machineDetail.setMealFee(mealFeeSum);
                machineDetail.setAccessoryFee(accessoryFeeSum);
                machineDetail.setPenalty(penaltySum);
                machineDetail.setReward(rewardSum);
                machineDetail.setProfit(profitSum);
                //重置
                tripNumSum = 0;
                transportDistanceSum = 0;
                loadTransportIncomeSum = 0;
                biaoxiangCarSum = 0;
                volumeSum = 0;
                loadCoalIncomeSum = 0;
                grossIncomeSum = 0;
                fuelFeeSum = 0;
                salarySum = 0;maintenanceFeeSum = 0;mealFeeSum = 0;accessoryFeeSum = 0;penaltySum = 0;rewardSum = 0;
                profitSum = 0;

                allSameCarTotal.add(j,machineDetail);
                //跳过新增的合计记录的索引
                i++;
                j++;
                flag = j;//指向下一车号的第一个
            } else {
                continue;
            }
        }
        return allSameCarTotal;
    }
}
