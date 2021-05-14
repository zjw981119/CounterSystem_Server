package com.gradproject.server.controller;

import com.gradproject.server.entity.AuxMacConfig;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.AuxMacConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AuxMachine")
public class AuxMacConfigController {

    private static final Logger logger = LoggerFactory.getLogger(AuxMacConfigController.class);

    @Autowired
    private AuxMacConfigService configService;

    //提交修改信息
    @PostMapping("/setConfig")
    public SelfResponse SetConfigData(@RequestBody List<AuxMacConfig> auxmacConfigList,
                                      @RequestParam(required = true,value = "timevalue") String queryTime){
        SelfResponse response = new SelfResponse();
        try {
            //List<CarConfig> carConfigList= JSONObject.parseObject(carConfigString,CarConfig.class);
            logger.info("接收到的修改数据为：【{}】,修改时间为：【{}】",auxmacConfigList,queryTime);
            response=configService.setConfigByTime(auxmacConfigList, queryTime);
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
