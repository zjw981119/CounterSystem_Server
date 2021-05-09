package com.gradproject.server.controller;

import com.gradproject.server.entity.User;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(SelfCounterController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public SelfResponse login(@RequestBody User user){
        logger.info("用户名：【{}】, 密码：【{}】", user.getUsername(),user.getPassword());
        SelfResponse response = new SelfResponse();
        response= userService.matchAdmin(user);
        return response;
    }
}
