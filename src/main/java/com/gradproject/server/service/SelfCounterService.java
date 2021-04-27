package com.gradproject.server.service;

import com.alibaba.fastjson.JSON;
import com.gradproject.server.dao.SelfCounterMapper;
import com.gradproject.server.entity.SelfCounter;
import com.gradproject.server.entity.model.ReturnCode;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SelfCounterService {
    private static final Logger logger = LoggerFactory.getLogger(SelfCounterService.class);

    @Autowired
    private FileService fileService;

    @Resource
    private SelfCounterMapper mapper;

    @Autowired
    private RfidService rfidService;

    /**
     * 自研计数工具插入一条计数数据
     *
     * @param counter
     * @return
     */
    public SelfResponse insetCounterData(SelfCounter counter) {
        logger.info("插入一条计数数据，数据为：【{}】", JSON.toJSONString(counter));
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(counter) || StringUtils.isEmpty(counter.getRfid())
                || StringUtils.isEmpty(counter.getTime())) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        try {
            //根据rfid查询车号
            String carNum = rfidService.findCarNum(counter.getRfid());
            if (StringUtils.isEmpty(carNum)) {
                logger.info("此卡号【{}】不在序列中。", counter.getRfid());
                return response.success("此卡不在序列中");
            }
            counter.setCarNum(carNum);
            //将base64格式的图片存储为txt文件
            if (StringUtils.isEmpty(counter.getPicture())) {
                counter.setPicture("暂无");
            } else {
                String picUrl = fileService.savePicture(counter.getPicture(), counter.getRfid());
                counter.setPicture(picUrl);
            }
            //根据采集器所在地址，截取矿区信息
            String address = counter.getAddress();
            if (!StringUtils.isEmpty(address)) {
                counter.setMiningArea(address.substring(0, address.contains("@") ? address.indexOf("@") : address.length()));
            }
            //根据当前时间判定是白班还是夜班
            //白班时间早八点-晚六点
            Date recordTime = DateUtils.getDate(counter.getTime(), DateUtils.FORMAT_LONG);
            //获取当前天 形如：yyyy-MM-dd
            String currentDay = DateUtils.getFormatDate(recordTime, DateUtils.FORMAT_SHORT);
            Date beginTime = DateUtils.getDate(currentDay + " 06:00:00", DateUtils.FORMAT_LONG);
            Date endTime = DateUtils.getDate(currentDay + " 05:59:59", DateUtils.FORMAT_LONG);
            String queryBeginTime, queryEndTime;
            //获得计数时间段
            if (recordTime.getTime() < beginTime.getTime()) {
                //前一天06:00:00~今天05:59:59
                Date preDay = DateUtils.addDays(recordTime, -1);
                String preDayStr = DateUtils.getFormatDate(preDay, DateUtils.FORMAT_SHORT);
                queryBeginTime = preDayStr + " 06:00:00";
                queryEndTime = DateUtils.getFormatDate(endTime, DateUtils.FORMAT_LONG);
            } else {
                //今天06:00:00~第二天05:59:59
                Date nextDay = DateUtils.addDays(recordTime, 1);
                String nextDayStr = DateUtils.getFormatDate(nextDay, DateUtils.FORMAT_SHORT);
                queryBeginTime = DateUtils.getFormatDate(beginTime, DateUtils.FORMAT_LONG);;
                queryEndTime = nextDayStr + " 05:59:59";
            }
            logger.info("当前车号：【{}】, 计数时间段为：【{}-{}】", carNum, queryBeginTime, queryEndTime);
            //构造计数的查询条件
//            SelfCounterExample example = new SelfCounterExample();
//            SelfCounterExample.Criteria criteria = example.createCriteria();
//            //计量次数查询条件(值班时间段、卡号)
//            criteria.andTimeBetween(DateUtils.getFormatDate(queryBeginTime, null),
//                    DateUtils.getFormatDate(queryEndTime, null));
//            criteria.andCardNoEqualTo(counter.getCardNo());
            int counterDegree = mapper.selectCounts(counter.getRfid(), queryBeginTime, queryEndTime);
            //设置次数
            counter.setSubCounts("" + (counterDegree + 1));
            /*
            空满载默认值 -1
            if (counter.getIsFull() == 0) {
                counter.setIsFull(-1);
            }
             */

            logger.info("插入数据库中的数据为：【{}】", JSON.toJSONString(counter));
            int result = mapper.insertCounterData(counter);
            if (result > 0) {
                logger.info("自研计数统计数据插入成功！插入的id为：【{}】", counter.getId());
            }

            return response.success(counter.getId());
//            return response.success("OK");
        } catch (Exception e) {
            logger.error("数据库插入申请失败，异常原因为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据库插入异常");
    }

}
