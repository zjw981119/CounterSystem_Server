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


        List<Warning> warnings;
        SelfResponse response = new SelfResponse();
        warnings = warnMapper.selectWarn(value1, value2);
        return response.success(warnings);


    }

}
