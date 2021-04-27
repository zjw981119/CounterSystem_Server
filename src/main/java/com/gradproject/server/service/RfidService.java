package com.gradproject.server.service;

import com.alibaba.fastjson.JSON;
import com.gradproject.server.dao.RfidMapper;
import com.gradproject.server.entity.RfidCarNum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RfidService {
    private static final Logger logger = LoggerFactory.getLogger(RfidService.class);

    @Resource
    private RfidMapper mapper;

    /**
     * 根据rfid查询绑定的车号
     *
     * @param rfid
     * @return
     * @throws Exception
     */
    public String findCarNum(String rfid) throws Exception {
        if(StringUtils.isEmpty(rfid)){
            throw new Exception("Rfid 不可为空");
        }
        List<RfidCarNum> rfidCarNumList = mapper.selectCarByRfid(rfid);
        //滤除过期数据
        filterRfidCarNum(rfidCarNumList);
        if(rfidCarNumList.isEmpty()){
            return "";
        }
        RfidCarNum rfidCarNum = rfidCarNumList.get(0);
        logger.info("查询到的rfid与车辆关联信息为：【{}】", rfidCarNum);
        return rfidCarNum.getCarNum();
    }

    /**
     * 获取数据库中所有的rfid与车号的映射关系
     *
     * @param address
     * @return
     */
    public String getInitCacheMessage(String address) throws Exception {
        if(StringUtils.isEmpty(address)){
            throw new Exception("地址不可为空！");
        }
        List<RfidCarNum> rfidCarNumList = mapper.selectRfidByAddress(address);
        filterRfidCarNum(rfidCarNumList);

        Map<String, String> rfidMap = new HashMap<>();
        for (RfidCarNum rfid : rfidCarNumList) {
            rfidMap.put(rfid.getRfid(), rfid.getCarNum());
        }
        return JSON.toJSONString(rfidMap);
    }

    /**
     * 滤除过期的Rfid卡&重复配的卡
     *
     * @param rfidCardNumList
     * @return
     */
    private List<RfidCarNum> filterRfidCarNum(List<RfidCarNum> rfidCardNumList){
        Iterator<RfidCarNum> iterator = rfidCardNumList.iterator();
        Set<String> rfidSet = new HashSet<>();
        while (iterator.hasNext()){
            RfidCarNum tmp = iterator.next();
            /*
            if(tmp.getEndTime() != null){
                //过滤已经超过停用时间的记录
                Date now = new Date();
                if(now.getTime() > tmp.getEndTime().getTime()){
                    iterator.remove();
                    continue;
                }
            }
             */
            if(!rfidSet.contains(tmp.getRfid())){
                rfidSet.add(tmp.getRfid());
            }else {
                iterator.remove();
            }
        }
        return rfidCardNumList;
    }
}
