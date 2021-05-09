package com.gradproject.server.dao;

import com.gradproject.server.entity.RfidCarNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户名查询密码
    @Select("select password from admin_user where username = #{username}")
    String selectPasswordByName(@Param("username") String username);
}
