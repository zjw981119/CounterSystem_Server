package com.gradproject.server.controller;


import com.gradproject.server.entity.MachineDetail;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.MachineDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/display")
@Slf4j
public class MachineDetailController {

    @Autowired
    private MachineDetailService machineDetailService;

    /**
     * 获得机械明细表展示数据
     * @param machineDetail
     * @return
     */
    @PostMapping("/machinedetail")
    public SelfResponse selectMachineDetail(@RequestBody MachineDetail machineDetail){
        log.info("查询参数为{},{},{},{}",machineDetail.getBeginTime(),
                machineDetail.getEndTime(),machineDetail.getTrackType(),machineDetail.getTrackNo());
        return machineDetailService.selectMachineDetail(machineDetail.getBeginTime(),
                machineDetail.getEndTime(),machineDetail.getTrackType(),machineDetail.getTrackNo());
    }

   @PostMapping("/ExcavatorAndCar")
    public SelfResponse selectExcavatorAndCar(){

        return machineDetailService.selectExcavatorAndCar();
   }

}
