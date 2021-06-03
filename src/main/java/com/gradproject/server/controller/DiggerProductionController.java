package com.gradproject.server.controller;

import com.gradproject.server.entity.ReWaji;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.DiggerProductionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/diggerProduction")
public class DiggerProductionController {
/**
 * @ClassName DiggerProductionController
 * @Description TODO
 * @Author tys
 * @Date 2021/6/2 10:10
 * @Version 1.0
 **/
    @Autowired
    private DiggerProductionService diggerProductionService;

    @PostMapping("/queryConditional")
    public SelfResponse queryConditional(@RequestParam("date") String date,@RequestParam("diggerNo") String diggerNo){
        log.info("查询条件为：【{}】，【{}】",date,diggerNo);
        SelfResponse aa=diggerProductionService.queryConditional(date,diggerNo);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }
    @PostMapping("/editDiggerProduction")
    public SelfResponse editDiggerProduction(@RequestBody List<ReWaji> diggerList){
        log.info("查询条件为：【{}】，【{}】",diggerList);
        SelfResponse aa=diggerProductionService.editDiggerProduction(diggerList);
        log.info("最终返回结果为：【{}】",aa.getData());
        return aa;
    }
//    @PostMapping("/editDiggerProduction")
//    public SelfResponse editDiggerProduction(@RequestBody ReWaji reWaji){
//        log.info("查询条件为：【{}】，【{}】",reWaji);
//        SelfResponse aa=diggerProductionService.editDiggerProduction(reWaji);
//        log.info("最终返回结果为：【{}】",aa.getData());
//        return aa;
//    }
}
