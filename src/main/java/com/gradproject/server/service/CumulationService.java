package com.gradproject.server.service;

import com.alibaba.fastjson.JSON;
import com.gradproject.server.dao.CumulationCounterMapper;
import com.gradproject.server.dao.RfidMapper;
import com.gradproject.server.entity.CumulationCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CumulationService {
    private static final Logger logger = LoggerFactory.getLogger(CumulationService.class);

    @Resource
    private CumulationCounterMapper mapper;

    public List<CumulationCounter> getCumulationData(String beginTime,String endTime){
        //logger.info("查询时间为：【{}至{}】。", beginTime,endTime);
        List<CumulationCounter> CarnumCountsList=mapper.selectCarnumCounts(beginTime,endTime);
        logger.info("所有的车辆运输数据为：【{}】", JSON.toJSONString(CarnumCountsList));
        return CarnumCountsList;
    }
}
