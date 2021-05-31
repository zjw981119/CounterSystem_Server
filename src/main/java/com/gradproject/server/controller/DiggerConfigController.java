package com.gradproject.server.controller;

import com.gradproject.server.entity.DiggerConfig;
import com.gradproject.server.entity.ReWajiConfig;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.DiggerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public SelfResponse queryConditional(@RequestBody ReWajiConfig diggerConfig){
        log.info("DiggerConfig实体类为：【{}】",diggerConfig);
        SelfResponse aa=diggerConfigService.editDiggerConfig(diggerConfig);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }
}
