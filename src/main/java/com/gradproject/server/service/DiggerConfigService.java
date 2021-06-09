package com.gradproject.server.service;

import com.gradproject.server.dao.DiggerConfigMapper;
import com.gradproject.server.dao.ReWajiConfigMapper;
import com.gradproject.server.entity.CarConfig;
import com.gradproject.server.entity.DiggerConfig;
import com.gradproject.server.entity.ReWajiConfig;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class DiggerConfigService {
    @Resource
    private DiggerConfigMapper diggerConfigMapper;
    @Resource
    private ReWajiConfigMapper reWajiConfigMapper;

    public SelfResponse queryConditional(String date) {
        SelfResponse selfResponse=new SelfResponse();
        List<ReWajiConfig> diggerConfig;
        try {
            diggerConfig=reWajiConfigMapper.selectByDate(date);
            log.info("数据查询为：【{}】",diggerConfig);
            return selfResponse.success(diggerConfig);
        }catch (Exception e){
            log.error("数据查询异常，异常信息为：【{}】",e.getMessage(),e);
        }
        return selfResponse.failure("数据异常，查询失败");
    }

    public SelfResponse editDiggerConfig(List<ReWajiConfig> diggerConfigList) {
        SelfResponse selfResponse=new SelfResponse();
        List<ReWajiConfig> reWajiConfigList=reWajiConfigMapper.selectByDate(diggerConfigList.get(0).getDate());
        //logger.info("获取数据库中记录的长度为【{}】",DBlist.size());
        //判断车辆配置表中是否有该日记录,若存在记录，则删除再插入，若不存在记录，则直接追加配置信息。
        if(reWajiConfigList.size()==0){
            //遍历列表，追加配置信息
            for(int i=0;i<diggerConfigList.size();i++){
                ReWajiConfig reWajiConfig = diggerConfigList.get(i);
                try{
                    int insertResult=reWajiConfigMapper.insert(reWajiConfig);
                    if (insertResult <= 0) {
                        log.info("配置信息修改失败");
                        return selfResponse.failure("数据库插入失败");
                    }
                }catch (Exception e){
                    return selfResponse.failure("数据库修改错误");
                }
            }
            return selfResponse.success("数据库修改成功");
        }
        else{
            //删除当日配置信息
            try{
                int deleteResult= reWajiConfigMapper.deleteByDate(diggerConfigList.get(0).getDate());
                if(deleteResult<=0){
                    log.info("删除配置信息修改失败");
                    return selfResponse.failure("数据库删除异常");
                }
            }catch (Exception e){
                return selfResponse.failure("数据库删除出错");
            }
            //遍历列表，追加配置信息
            try{
                for(int i=0;i<diggerConfigList.size();i++){
                    ReWajiConfig reWajiConfig = diggerConfigList.get(i);
                    int insertResult = reWajiConfigMapper.insert(reWajiConfig);
                    if (insertResult <= 0) {
                        log.info("配置信息修改失败");
                        return selfResponse.failure("数据库插入异常");
                    }
                }
            }catch (Exception e){
                return selfResponse.failure("数据库插入错误");
            }
            return selfResponse.success("数据库修改成功");
        }
    }

    public SelfResponse delDiggerConfigById(Integer id) {
        SelfResponse selfResponse=new SelfResponse();
        try{
            int i=reWajiConfigMapper.deleteByPrimaryKey(id);
            log.info("数据删除结果为：【{}】",i);
            return selfResponse.success();
        }catch (Exception e){
            log.error("数据删除异常，异常信息为：【{}】",e.getMessage(),e);
        }
        return selfResponse.failure("数据删除异常");
    }

    public SelfResponse latestDiggerConfigData() {
        SelfResponse selfResponse=new SelfResponse();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        log.info("初始查询时间为【{}】",calendar.getTime());
        int circleTimes=30;
        try{
            while(circleTimes>0){
                calendar.add(Calendar.DATE, -1);
                String formatShortStr = DateUtils.getFormatDate(calendar.getTime(),"yyyy-MM-dd");
                log.info("查询时间为【{}】",formatShortStr);
                List<ReWajiConfig> list=reWajiConfigMapper.selectByDate(formatShortStr);
                if(list.size()!=0){
                    return selfResponse.success(list);
                }
                circleTimes--;
            }
            return selfResponse.failure("配置信息查询失败，请先配置挖机信息");
        }catch (Exception e){
            log.error("查询出错：【{}】",e.getMessage());
            return selfResponse.failure("配置信息查询失败，请刷新重试");
        }
    }
}
