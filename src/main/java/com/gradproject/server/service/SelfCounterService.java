package com.gradproject.server.service;

import com.alibaba.fastjson.JSON;
import com.gradproject.server.dao.CumulationCounterMapper;
import com.gradproject.server.dao.SelfCounterMapper;
import com.gradproject.server.entity.CumulationCounter;
import com.gradproject.server.entity.RfidCarNum;
import com.gradproject.server.entity.SelfCounter;
import com.gradproject.server.entity.model.ReturnCode;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.utils.DateUtils;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class SelfCounterService {
    private static final Logger logger = LoggerFactory.getLogger(SelfCounterService.class);

    @Autowired
    private FileService fileService;

    @Resource
    private SelfCounterMapper Smapper;

    @Resource
    private CumulationCounterMapper Cmapper;

    @Autowired
    private RfidService rfidService;

    /**
     * 插入一条计数数据
     *
     * @param counter
     * @return
     */
    public SelfResponse insetCounterData(SelfCounter counter, CumulationCounter Cucounter) {
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
            //每一班时间为当天早上06:00:00-第二天早上05:59:59
            Date recordTime = DateUtils.getDate(counter.getTime(), DateUtils.FORMAT_LONG);
            //获取当前天 形如：yyyy-MM-dd
            String currentDay = DateUtils.getFormatDate(recordTime, DateUtils.FORMAT_SHORT);
            Date beginTime = DateUtils.getDate(currentDay + " 06:00:00", DateUtils.FORMAT_LONG);
            Date endTime = DateUtils.getDate(currentDay + " 05:59:59", DateUtils.FORMAT_LONG);
            String queryBeginTime, queryEndTime, workDay;
            //获得计数时间段
            if (recordTime.getTime() < beginTime.getTime()) {
                //前一天06:00:00~今天05:59:59
                Date preDay = DateUtils.addDays(recordTime, -1);
                String preDayStr = DateUtils.getFormatDate(preDay, DateUtils.FORMAT_SHORT);
                workDay=preDayStr;
                queryBeginTime = preDayStr + " 06:00:00";
                queryEndTime = DateUtils.getFormatDate(endTime, DateUtils.FORMAT_LONG);
            } else {
                //今天06:00:00~第二天05:59:59
                Date nextDay = DateUtils.addDays(recordTime, 1);
                String nextDayStr = DateUtils.getFormatDate(nextDay, DateUtils.FORMAT_SHORT);
                workDay=DateUtils.getFormatDate(beginTime,DateUtils.FORMAT_SHORT);
                queryBeginTime = DateUtils.getFormatDate(beginTime, DateUtils.FORMAT_LONG);;
                queryEndTime = nextDayStr + " 05:59:59";
            }
            //设置统计表的车号、运输日
            Cucounter.setCarNum(carNum);
            Cucounter.setWorkDay(workDay);
            logger.info("当前车号：【{}】, 计数时间段为：【{}-{}】, 车辆工作日期为：【{}】", carNum, queryBeginTime, queryEndTime,workDay);
            //构造计数的查询条件
//            SelfCounterExample example = new SelfCounterExample();
//            SelfCounterExample.Criteria criteria = example.createCriteria();
//            //计量次数查询条件(值班时间段、卡号)
//            criteria.andTimeBetween(DateUtils.getFormatDate(queryBeginTime, null),
//                    DateUtils.getFormatDate(queryEndTime, null));
//            criteria.andCardNoEqualTo(counter.getCardNo());
            int counterDegree = Smapper.selectCounts(counter.getRfid(), queryBeginTime, queryEndTime);
            //设置次数
            counter.setSubCounts("" + (counterDegree + 1));
            Cucounter.setCounts(counterDegree + 1);



            //空满载默认值 空载
            if (counter.getCarLoad() == null) {
                counter.setCarLoad("空载");
            }

            //向self_counter数据表中插入数据
            logger.info("插入self_counter数据库中的数据为：【{}】", JSON.toJSONString(counter));
            int Sresult = Smapper.insertCounterData(counter);
            if (Sresult > 0) {
                logger.info("数据插入self_counter表成功！插入的id为：【{}】", counter.getId());
            }

            //向cumulation_counter数据表中插入数据
            logger.info("插入cumulation_counter数据库中的数据为：【{}】", JSON.toJSONString(Cucounter));
            List<CumulationCounter> CuCounterList=Cmapper.selectCumulateDataByNumAndDay(carNum,workDay);
            //若为空，则插入数据，否则更新数据
            int Cresult;
            if(CuCounterList.isEmpty())
            {
                Cresult=Cmapper.insertCumulateData(Cucounter);
            }
            else{
                Cresult=Cmapper.updateCumulateData(Cucounter);
            }
            if(Cresult>0){
                logger.info("数据插入cumulation_counter表成功！");
            }

            return response.success();
//            return response.success("OK");
        } catch (Exception e) {
            logger.error("数据库插入申请失败，异常原因为：【{}】", e.getMessage(), e);
        }
        return response.failure("数据库插入异常");
    }

    /**
     * 根据id修改车载情况
     *
     * @param IdList,carLoad
     * @return
     */
    public SelfResponse setCarLoadByIdList(Integer[] IdList,String carLoad){
        logger.info("Id列表为：【{}】", Arrays.toString(IdList));
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (IdList.length==0) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        int result=0;
        //遍历数组，修改车载情况
        for(int i=0;i<IdList.length;i++){
            result=Smapper.updateCarloadDataById(carLoad,IdList[i]);
            if (result <= 0) {
                logger.info("车载情况修改失败");
                return response.failure("数据库修改异常");
            }
        }
        return response.success();
    }

    /**
     * 查询矿车工作记录
     *
     * @param beginTime,endTime,carNum
     * @return
     */
    public List<SelfCounter> selectRecord(String beginTime,String endTime,String carNum){
        List<SelfCounter> CounterRecordList;
        //查询车号为空，则查询时间段内所有记录
        if (carNum.equals("")){
            CounterRecordList=Smapper.selectRecordByTime(beginTime, endTime);
        }
        //查询车号不为空，则只查询该车在时间段内的工作记录
        else {
            CounterRecordList=Smapper.selectRecordByNumAndTime(carNum,beginTime,endTime);
        }
        logger.info("查询到的数据有【{}】条", CounterRecordList.size());
        logger.info("查询的工作记录为：【{}】", CounterRecordList);
        //遍历返回的列表，读取本地txt文本，将保存的base64编码返回给前端
        Iterator<SelfCounter> it=CounterRecordList.iterator();
        while (it.hasNext()){
            SelfCounter Counter=it.next();
            String Base64Pic=fileService.getBase64(Counter.getId());
            Counter.setPicture(Base64Pic);
        }
        return  CounterRecordList;

    }

    /**
     * 根据指定id查询计数数据
     *
     * @param id
     * @return
     */
    public String findCounterPicById(Integer id) {
        if (id == null || id == 0) {
            return null;
        }
        return Smapper.selectPicById(id);
    }

}
