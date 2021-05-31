package com.gradproject.server.service;

import com.gradproject.server.dao.CarMapper;
import com.gradproject.server.entity.CarConfig;
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
public class CarConfigService {

    private static final Logger logger = LoggerFactory.getLogger(CarConfigService.class);

    @Resource
    private CarMapper mapper;

    /**
     * 根据日期修改车辆配置表
     *
     * @param CarConfigList
     * @return
     */
    public SelfResponse setConfigByTime(List<CarConfig> CarConfigList, String updateTime){
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(CarConfigList)||StringUtils.isEmpty(updateTime)) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }
        int insertResult=0;
        int deleteResult=0;

        List<CarConfig> DBlist=mapper.SelectConfigByDate(updateTime);
        //logger.info("获取数据库中记录的长度为【{}】",DBlist.size());

        //判断车辆配置表中是否有该日记录,若存在记录，则删除再插入，若不存在记录，则直接追加配置信息。
        if(DBlist.size()==0){
            //遍历列表，追加配置信息
            for(int i=0;i<CarConfigList.size();i++){
                CarConfig config = CarConfigList.get(i);
                //设置更新日期属性值
                config.setUpdateTime(updateTime);
                logger.info("获取的对象为：【{}】",config);
                insertResult=mapper.insertCarConfigData(config);
                if (insertResult <= 0) {
                    logger.info("配置信息修改失败");
                    return response.failure("数据库修改异常");
                }
            }
        }
        else{
            //删除当日配置信息，并插入新的配置信息
            deleteResult= mapper.deleteConfigByTime(updateTime);
            if(deleteResult<=0){
                logger.info("删除配置信息修改失败");
                return response.failure("数据库删除异常");
            }

            //遍历列表，追加配置信息
            for(int i=0;i<CarConfigList.size();i++){
                CarConfig config = CarConfigList.get(i);
                //设置更新日期属性值
                config.setUpdateTime(updateTime);
                logger.info("获取的对象为：【{}】",config);
                insertResult=mapper.insertCarConfigData(config);
                if (insertResult <= 0) {
                    logger.info("配置信息修改失败");
                    return response.failure("数据库修改异常");
                }
            }
        }

        return response.success();
    }

    /**
     * 根据日期查询车辆配置表
     *
     * @param queryTime
     * @return
     */
    public SelfResponse selectConfigByTime(String queryTime){
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (StringUtils.isEmpty(queryTime)) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }

        //获取最新更新日期
        String newestDate=mapper.SelectNewestTime(queryTime);
        //获取配置列表
        List<CarConfig> carConfigList=mapper.SelectConfigByDate(newestDate);
        logger.info("获取车辆配置信息为【{}】",carConfigList);
        return response.success(carConfigList);
    }

    /**
     * 根据id删除配置信息
     *
     * @param id
     * @return
     */
    public SelfResponse deleteConfigByID(Integer id){
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(id)) {
            return response.failure(ReturnCode.DATA_MISS.getMsg());
        }

        int result=mapper.deleteConfigByID(id);
        if (result > 0) {
            logger.info("配置信息删除成功");
            return response.success();
        }
        else {
            logger.info("rfid不存在，数据删除失败");
            return response.failure("rfid不存在");
        }
    }




}
