package com.gradproject.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.gradproject.server.entity.ChangeCarLoad;
import com.gradproject.server.entity.CumulationCounter;
import com.gradproject.server.entity.SelfCounter;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.CumulationService;
import com.gradproject.server.service.RfidService;
import com.gradproject.server.service.SelfCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/counter")
public class SelfCounterController {
    private static final Logger logger = LoggerFactory.getLogger(SelfCounterController.class);

    //@Autowired
    //private SelfCounterService counterService;

    @Autowired
    private SelfCounterService counterService;

    @Autowired
    private RfidService rfidService;

    @Autowired
    private CumulationService cumulateService;

    //获取车辆工作记录
    @GetMapping("/getRecord")
    public SelfResponse getRecordData(@RequestParam(required = true,value = "beginTime") String beginTime,
                                      @RequestParam(required = true,value = "endTime") String endTime,
                                      @RequestParam(required = false,value = "queryCar") String queryCar){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("查询时间为：【{}至{}】,查询车号为：【{}】", beginTime,endTime,queryCar);
            List<SelfCounter> cache= counterService.selectRecord(beginTime, endTime, queryCar);
            //List<CumulationCounter> cache=cumulateService.getCumulationData(beginTime, endTime);
            logger.info("返回的工作记录数据为：【{}】", cache);
            return response.success(cache);
        } catch (Exception e){
            logger.error("数据查询异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据查询异常");
    }

    //获取车辆运输统计数据
    @GetMapping("/getData")
    public SelfResponse getCountData(@RequestParam(required = true,value = "beginTime") String beginTime,
                                     @RequestParam(required = true,value = "endTime") String endTime){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("查询时间为：【{}至{}】。", beginTime,endTime);
            List<CumulationCounter> cache=cumulateService.getCumulationData(beginTime, endTime);
            logger.info("返回的统计数据为：【{}】", cache);
            return response.success(cache);
        } catch (Exception e){
            logger.error("数据查询异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据查询异常");
    }

    @GetMapping("/initConfig")
    public SelfResponse getInitRfidMessage(@RequestParam("address") String address){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取矿区：【{}】的配置信息。", address);
            String cache = rfidService.getInitCacheMessage(address);
            logger.info("返回的配置信息为：【{}】", cache);
            return response.success(cache);
        } catch (Exception e){
            logger.error("数据查询异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据查询异常");
    }

    //上传车辆运输记录及图片
    @PostMapping("/add")
    public SelfResponse receiveThirdPartyData(@RequestBody String counterString){
        SelfResponse response = new SelfResponse();
        try {
            SelfCounter counter = JSONObject.parseObject(counterString, SelfCounter.class);
            CumulationCounter Cucounter=new CumulationCounter();
            logger.info("自研计数模块收到的数据为：【{}】", counter.toString());

            //todo 根据查询车辆相关信息
            //插入计数数据
            response = counterService.insetCounterData(counter,Cucounter);
            return response;
        } catch (Exception e){
            logger.error("数据插入异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据插入异常");
    }

    //上传车辆运输记录及图片
    @PostMapping("/setCarload")
    public SelfResponse receiveCarID(@RequestBody String CarLoadString){
        SelfResponse response = new SelfResponse();
        try {
            ChangeCarLoad Carload=JSONObject.parseObject(CarLoadString,ChangeCarLoad.class);
            logger.info("接收到的车载对象数据为：【{}】",Carload);
            response=counterService.setCarLoadByIdList(Carload.getIdList(),Carload.getCarLoad());
            return response;
        } catch (Exception e){
            logger.error("数据插入异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据插入异常");
    }

}
