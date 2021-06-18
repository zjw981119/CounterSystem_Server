package com.gradproject.server.service;

import com.gradproject.server.dao.WarnMapper;
import com.gradproject.server.entity.Warning;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class WarnService {
    @Resource
    private WarnMapper warnMapper;
    public SelfResponse selectWarn(
                                String value1,
                                String value2
                                ){

        Date startTime;
        Date endTime;
        List<Warning> warnings;
        SelfResponse response = new SelfResponse();
//        try {
//            startTime = DateUtils.getDate(value1, "yyyy-MM-dd HH:mm:ss");
//            endTime = DateUtils.getDate(value2, "yyyy-MM-dd HH:mm:ss");
//            log.info("转化后的数据startTime:{},endTime:{}", startTime, endTime);
//        } catch (Exception e) {
//            log.info("时间数据格式化错误：{},{}", e.getMessage(), e);
//            return response.error("格式转化错误");
//        }
        warnings = warnMapper.selectWarn(value1,value2);
        return response.success(warnings);


    }

}
