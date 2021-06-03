package com.gradproject.server.controller;

import com.gradproject.server.entity.Warning;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.WarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warn")
public class WarnController {
    @Autowired
    private WarnService warnService;
    //历史告警查询
    @PostMapping("/findAll")
    public SelfResponse findAll(@RequestBody Warning warn){
        System.out.println( warn.getValue1()+","+warn.getValue2());
        return warnService.selectWarn(
                                        warn.getValue1(),warn.getValue2());
    }

}
