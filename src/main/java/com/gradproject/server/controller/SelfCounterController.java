package com.gradproject.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.gradproject.server.entity.CumulationCounter;
import com.gradproject.server.entity.SelfCounter;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.RfidService;
import com.gradproject.server.service.SelfCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    //上传车辆运输记录及图片
    @GetMapping("/getData")
    public SelfResponse getCountData(@RequestParam("beginTime") String beginTime,
                                     @RequestParam("endTime") String endTime){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("开始时间：【{}】。", beginTime);
            logger.info("结束时间：【{}】。", endTime);

        } catch (Exception e){
            logger.error("数据插入异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据插入异常");
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

    @GetMapping("/initConfig")
    public SelfResponse getInitRfidMessage(@RequestParam("address") String address){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取矿区：【{}】的配置信息。", address);
            String cache = rfidService.getInitCacheMessage(address);
            logger.info("返回的配置信息为：【{}】", cache);
            return response.success(cache);
        } catch (Exception e){
            logger.error("数据插入异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据插入异常");
    }
}
