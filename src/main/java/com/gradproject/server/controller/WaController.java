package com.gradproject.server.controller;

import com.gradproject.server.entity.Waing;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.WaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wa")
public class WaController {
    @Autowired
    private WaService waService;
    //历史告警查询
    @PostMapping("/findAll")
    public SelfResponse findAll(@RequestBody Waing wa){
        System.out.println( wa.getValue1()+","+wa.getValue2());
        return waService.selectWa(
                                        wa.getValue1(),wa.getValue2());
    }

}
