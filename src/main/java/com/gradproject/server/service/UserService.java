package com.gradproject.server.service;


import com.gradproject.server.dao.UserMapper;
import com.gradproject.server.entity.User;
import com.gradproject.server.entity.model.ReturnCode;
import com.gradproject.server.entity.model.SelfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper mapper;
    /**
     * 登陆验证
     *
     * @param user
     * @return
     */
    public SelfResponse matchAdmin(User user){
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(user) ||StringUtils.isEmpty(user.getUsername())) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        String password=mapper.selectPasswordByName(user.getUsername());
        //判断用户名密码是否正确
        if(user.getPassword().equals(password)){
            return response.success("登陆成功");
        }
        else {
            return response.failure("用户名密码错误");
        }
    }

}
