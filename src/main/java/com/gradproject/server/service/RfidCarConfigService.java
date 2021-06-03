package com.gradproject.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gradproject.server.dao.RfidMapper;
import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.model.ReturnCode;
import com.gradproject.server.entity.model.SelfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RfidCarConfigService {
    private static final Logger logger = LoggerFactory.getLogger(RfidCarConfigService.class);

    @Resource
    private RfidMapper mapper;

    /**
     * 插入一条配置数据
     *
     * @param config
     * @return
     */
    public SelfResponse insetConfigData(RfidCarNum config){
        logger.info("插入一条计数数据，数据为：【{}】", JSON.toJSONString(config));
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(config) || StringUtils.isEmpty(config.getRfid())
                || StringUtils.isEmpty(config.getCarNum()) || StringUtils.isEmpty(config.getAddress())) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        //判断是否插入的rfid是否已存在
        List<RfidCarNum> rfidCarNumList = mapper.selectCarByRfid(config.getRfid());
        logger.info("查询到的数据库记录为：【{}】",rfidCarNumList);
        //如果查询结果不为空
        if(!rfidCarNumList.isEmpty()){
            logger.info("rfid已存在，数据插入失败");
            return response.failure("rfid已存在");
        }
        else {
            int result = mapper.insertConfigData(config);
            if (result > 0) {
                logger.info("自研计数统计数据插入成功！");
                return response.success();
            }
        }
        return response.failure("数据库插入异常");
    }

    /**
     * 修改一条配置数据
     *
     * @param config
     * @return
     */
    public SelfResponse changeConfigData(RfidCarNum config){
        logger.info("修改一条配置数据，数据为：【{}】", JSON.toJSONString(config));
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(config) || StringUtils.isEmpty(config.getRfid())) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        //判断是否插入的rfid是否已存在
        List<RfidCarNum> rfidCarNumList = mapper.selectCarByRfid(config.getRfid());
        logger.info("修改前的数据为：【{}】",rfidCarNumList);
        //如果查询结果不为空，可以修改
        if(!rfidCarNumList.isEmpty()){
            int result = mapper.setConfigData(config);
            if (result > 0) {
                logger.info("自研计数统计修改数据成功！");
                return response.success();
            }
        }
        else {
            logger.info("rfid不存在，数据修改失败");
            return response.failure("rfid不存在");
        }
        return response.failure("数据库插入异常");
    }

    /**
     * 删除一条配置数据
     *
     * @param rfid
     * @return
     */
    public SelfResponse removeConfigData(String rfid){
        logger.info("需要删除数据的rfid号为：【{}】", JSON.toJSONString(rfid));
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (StringUtils.isEmpty(rfid)) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        //判断需要删除的rfid是否已存在
        List<RfidCarNum> rfidCarNumList = mapper.selectCarByRfid(rfid);
        logger.info("需要删除的配置信息为：【{}】",rfidCarNumList);
        //如果查询结果不为空，可以删除
        if(!rfidCarNumList.isEmpty()){
            int result = mapper.deleteConfigData(rfid);
            if (result > 0) {
                logger.info("配置信息删除成功");
                return response.success();
            }
        }
        else {
            logger.info("rfid不存在，数据删除失败");
            return response.failure("rfid不存在");
        }
        return response.failure("数据库删除异常");
    }

    /**
     * 获取数据库中所有的rfid与车号的映射关系
     *
     * @param address,rfid
     * @return
     */
    public List<RfidCarNum> getConfigList(String address,String rfid) throws Exception {
        if(StringUtils.isEmpty(address)){
            throw new Exception("地址不可为空！");
        }
        List<RfidCarNum> rfidCarNumList;

        //分页展示
        //PageHelper.startPage(pageNum,pageSize);

        //sql语句需要在startpage()后执行，不然没用
        rfidCarNumList= mapper.selectRfidByAddressOrRfid(address,rfid);
        //PageInfo<RfidCarNum> pi= new PageInfo<>(rfidCarNumList);
        //logger.info("分页获得的列表为：【{}】。",pi.getList());

        //return JSON.toJSONString(rfidCarNumList);
        //return pi.getList();
        return rfidCarNumList;
    }

    /**
     * 获取数据库中所有不重复的车号列表
     *
     * @param address
     * @return
     */
    public String[] getCarnumList(String address) throws Exception {
        if(StringUtils.isEmpty(address)){
            throw new Exception("地址不可为空！");
        }
        String[] carList;
        carList= mapper.selectAllCarnum(address);
        return carList;
    }

    /**
     * 获取RFID配置信息总数
     *
     * @param address,rfid
     * @return
     */
    public int getCountConfig(String address,String rfid) throws Exception {
        if(StringUtils.isEmpty(address)){
            throw new Exception("地址不可为空！");
        }
        //List<RfidCarNum> rfidCarNumList;
        int total;
        total=mapper.CountConfigByAddressOrRfid(address, rfid);

        return total;
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
