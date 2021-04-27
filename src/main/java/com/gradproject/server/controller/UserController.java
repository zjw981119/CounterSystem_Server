package com.gradproject.server.controller;

import com.gradproject.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(SelfCounterController.class);

    @PostMapping("/login")
    public String login(@RequestBody User user){
        logger.info("用户名：【{}】", user.getName());
        logger.info("密码：【{}】", user.getPassword());
        if("admin".equals(user.getName())&& "123456".equals(user.getPassword())){
            return "success";
        }
        else{
            return "error";
        }
    }
}
