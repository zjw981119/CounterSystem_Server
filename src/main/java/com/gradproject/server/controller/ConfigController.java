package com.gradproject.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Rfidshow")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(SelfCounterController.class);

    @Autowired
    private ConfigService configService;

    //@RequestParam("address")
    @GetMapping("/initConfig")
    public SelfResponse getList(@RequestParam(required = true,value="address") String address,
                                @RequestParam(required = false,value = "query") String query,
                                @RequestParam(required = false,value = "pagenum") Integer pagenum,
                                @RequestParam(required = false,value = "pagesize") Integer pagesize){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取矿区：【{}】的配置信息。查询的rfid号为：【{}】。", address,query);
            logger.info("当前选中页数为：【{}】。页面数据容量为：【{}】。", pagenum,pagesize);
            List<RfidCarNum> cache = configService.getConfigList(address,query,pagenum,pagesize);
            int total= configService.getCountConfig(address, query);
            logger.info("返回的配置信息为：【{}】", cache);
            logger.info("配置表数据总数为：【{}】条。",total);
            //logger.info("响应为：【{}】。", response.success(cache,total));
            return response.success(cache,total);
        } catch (Exception e){
            logger.error("数据读取异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据读取异常");
    }

    //删除配置信息
    @GetMapping("/delete")
    public SelfResponse deleteConfigData(@RequestParam(required = true,name="rfid") String rfid){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取rfid号为：【{}】。", rfid);
            response = configService.removeConfigData(rfid);
            return response;
        } catch (Exception e){
            logger.error("数据读取异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据删除异常");
    }

    //增加新的配置信息
    @PostMapping("/add")
    public SelfResponse addConfigData(@RequestBody String configString){
        SelfResponse response = new SelfResponse();
        try {
            RfidCarNum config = JSONObject.parseObject(configString, RfidCarNum.class);
            logger.info("自研计数模块收到的数据为：【{}】", config.toString());

            //todo 根据查询车辆相关信息
            //插入计数数据
            response = configService.insetConfigData(config);
            return response;
        } catch (Exception e){
            logger.error("数据插入异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据插入异常");
    }

    //修改配置信息
    @PostMapping("/update")
    public SelfResponse updateConfigData(@RequestBody String configString){
        SelfResponse response = new SelfResponse();
        try {
            RfidCarNum config = JSONObject.parseObject(configString, RfidCarNum.class);
            logger.info("自研计数模块收到的修改数据为：【{}】", config.toString());

            //todo 根据查询车辆相关信息
            //修改配置数据
            response = configService.changeConfigData(config);
            return response;
        } catch (Exception e){
            logger.error("数据修改异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据修改异常");
    }

}
