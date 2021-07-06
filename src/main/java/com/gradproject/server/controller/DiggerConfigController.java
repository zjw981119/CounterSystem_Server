package com.gradproject.server.controller;

import com.gradproject.server.entity.DiggerConfig;
import com.gradproject.server.entity.ReWajiConfig;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.DiggerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/diggerConfig")
public class DiggerConfigController {

    @Autowired
    private DiggerConfigService diggerConfigService;

    @PostMapping("/queryConditional")
    public SelfResponse queryConditional(@RequestParam("date") String date){
        System.out.println(date);
        log.info("查询时间为：【{}】",date);
        SelfResponse aa=diggerConfigService.queryConditional(date);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }
    @PostMapping("/editDiggerConfig")
    public SelfResponse editDiggerConfig(@RequestBody List<ReWajiConfig> diggerConfigLsit){
        log.info("DiggerConfig实体类为：【{}】",diggerConfigLsit);
        SelfResponse aa=diggerConfigService.editDiggerConfig(diggerConfigLsit);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }

    @PostMapping("/delDiggerConfigById/{id}")
    public SelfResponse delDiggerConfigById(@PathVariable("id") Integer id){
        log.info("DiggerConfig实体类为：【{}】",id);
        SelfResponse aa=diggerConfigService.delDiggerConfigById(id);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }

    @PostMapping("/latestDiggerConfigData")
    public SelfResponse latestDiggerConfigData(@RequestParam("date") String date){
        SelfResponse aa=diggerConfigService.latestDiggerConfigData(date);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }
}
