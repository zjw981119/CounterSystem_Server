package com.gradproject.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.gradproject.server.entity.CarConfig;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.CarConfigService;
import com.gradproject.server.service.RfidCarConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Carconfig")
public class CarConfigController {

    private static final Logger logger = LoggerFactory.getLogger(CarConfigController.class);

    @Autowired
    private CarConfigService configService;

    @Autowired
    private RfidCarConfigService rfidService;

    //提交修改信息
    @PostMapping("/setConfig")
    public SelfResponse SetConfigData(@RequestBody List<CarConfig> carConfigList,
                                      @RequestParam(required = true,value = "timevalue") String queryTime){
        SelfResponse response = new SelfResponse();
        try {
            //List<CarConfig> carConfigList= JSONObject.parseObject(carConfigString,CarConfig.class);
            logger.info("接收到的修改数据为：【{}】,修改时间为：【{}】",carConfigList,queryTime);
            response=configService.setConfigByTime(carConfigList, queryTime);
            return response.success();

            //return response;
        } catch (Exception e){
            logger.error("数据修改异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据修改异常");
    }

    //获取配置信息
    @GetMapping("/getConfig")
    public SelfResponse GetConfigData(@RequestParam(required = true,value = "timevalue") String date){
        SelfResponse response = new SelfResponse();
        try {
            //List<CarConfig> carConfigList= JSONObject.parseObject(carConfigString,CarConfig.class);
            logger.info("查询日期为：【{}】",date);
            response=configService.selectConfigByTime(date);
            return response;

            //return response;
        } catch (Exception e){
            logger.error("数据查询异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据查询异常");
    }

    //获取全部车号
    @GetMapping("/getCarNum")
    public SelfResponse getCarnumSelection(@RequestParam(required = true,value="address") String address){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取矿区：【{}】的车号信息。", address);
            String[] cache = rfidService.getCarnumList(address);
            //logger.info("响应为：【{}】。", response.success(cache,total));
            return response.success(cache);
        } catch (Exception e){
            logger.error("数据读取异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据读取异常");
    }

    //删除配置信息
    @GetMapping("/delete")
    public SelfResponse deleteConfigData(@RequestParam(required = true,name="id") Integer id){
        SelfResponse response = new SelfResponse();
        try {
            logger.info("获取id号为：【{}】。", id);
            response = configService.deleteConfigByID(id);
            return response;
        } catch (Exception e){
            logger.error("数据删除异常，异常信息为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据删除异常");
    }
}
