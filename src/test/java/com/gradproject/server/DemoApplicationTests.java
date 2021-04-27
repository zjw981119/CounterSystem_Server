package com.gradproject.server;


import com.gradproject.server.dao.RfidMapper;
import com.gradproject.server.entity.RfidCarNum;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {


    private RfidMapper rfidMapper;
    @Test
    void contextLoads() {
        //List<RfidCarNum> rfidCarNums = rfidMapper.selectList(null);
        //System.out.println(rfidCarNums.size());
        //rfidCarNums.forEach(System.out::println);



    }

}
