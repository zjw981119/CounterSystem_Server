package com.gradproject.server.service;

import com.gradproject.server.dao.ReWajiMapper;
import com.gradproject.server.entity.ReWaji;
import com.gradproject.server.entity.ReWajiConfig;
import com.gradproject.server.entity.model.SelfResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DiggerProductionService {

/**
 * @ClassName DiggerProductionService
 * @Description TODO
 * @Author tys
 * @Date 2021/6/2 10:15
 * @Version 1.0
 **/
    @Resource
    private ReWajiMapper reWajiMapper;

    public SelfResponse queryConditional(String date, String diggerNo) {
        SelfResponse selfResponse=new SelfResponse();
        List<ReWaji> reWajiList;
        try {
            reWajiList=reWajiMapper.selectByDateAndDiggerNo(date,diggerNo);
            log.info("数据查询为：【{}】",reWajiList);
            return selfResponse.success(reWajiList);
        }catch (Exception e){
            log.error("数据查询异常，异常信息为：【{}】",e.getMessage(),e);
        }
        return selfResponse.failure("数据异常，查询失败");
    }

    public SelfResponse editDiggerProduction(List<ReWaji> diggerList) {
        SelfResponse selfResponse=new SelfResponse();
        List<ReWajiConfig> reWajiList=reWajiMapper.selectByDate(diggerList.get(0).getDate());
        //logger.info("获取数据库中记录的长度为【{}】",DBlist.size());
        //判断车辆配置表中是否有该日记录,若存在记录，则删除再插入，若不存在记录，则直接追加配置信息。
        if(reWajiList.size()==0){
            //遍历列表，追加配置信息
            for(int i=0;i<diggerList.size();i++){
                ReWaji reWaji = diggerList.get(i);
                try{
                    int insertResult=reWajiMapper.insert(reWaji);
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
                int deleteResult= reWajiMapper.deleteByDate(diggerList.get(0).getDate());
                if(deleteResult<=0){
                    log.info("删除配置信息修改失败");
                    return selfResponse.failure("数据库删除异常");
                }
            }catch (Exception e){
                return selfResponse.failure("数据库删除出错");
            }
            //遍历列表，追加配置信息
            try{
                for(int i=0;i<diggerList.size();i++){
                    ReWaji reWaji = diggerList.get(i);
                    int insertResult = reWajiMapper.insert(reWaji);
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
            int i=reWajiMapper.deleteByPrimaryKey(id);
            log.info("数据删除结果为：【{}】",i);
            return selfResponse.success();
        }catch (Exception e){
            log.error("数据删除异常，异常信息为：【{}】",e.getMessage(),e);
        }
        return selfResponse.failure("数据删除异常");
    }

}
