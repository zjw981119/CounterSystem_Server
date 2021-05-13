package com.gradproject.server.service;

import com.gradproject.server.dao.CarMapper;
import com.gradproject.server.entity.CarConfig;
import com.gradproject.server.entity.model.ReturnCode;
import com.gradproject.server.entity.model.SelfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarConfigService {

    private static final Logger logger = LoggerFactory.getLogger(CarConfigService.class);

    @Resource
    private CarMapper mapper;
    /**
     * 根据更新时间修改车辆配置表
     *
     * @param CarConfigList
     * @return
     */
    public SelfResponse setConfigByTime(List<CarConfig> CarConfigList, String updateTime){
        SelfResponse response = new SelfResponse();
        //必传参数不可为空
        if (ObjectUtils.isEmpty(CarConfigList)) {
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


}
