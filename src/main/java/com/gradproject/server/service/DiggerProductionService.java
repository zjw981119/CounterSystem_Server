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
        for(int i=0;i<diggerList.size();i++){
            ReWaji reWaji = diggerList.get(i);
            try{
                int updateResult=reWajiMapper.updateByPrimaryKey(reWaji);
                if (updateResult <= 0) {
                    log.info("配置信息修改失败");
                    return selfResponse.failure("数据库更新失败");
                }
            }catch (Exception e){
                return selfResponse.failure("数据库更新错误");
            }
        }
            return selfResponse.success("数据库更新成功");
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

    public SelfResponse addDiggerProduction(List<ReWaji> diggerList) {
        SelfResponse selfResponse=new SelfResponse();
        for(int i=0;i<diggerList.size();i++){
            ReWaji reWaji = diggerList.get(i);
            try{
                int insertResult=reWajiMapper.insert(reWaji);
                if (insertResult <= 0) {
                    log.info("挖机生产情况插入成功");
                    return selfResponse.failure("挖机生产情况插入失败");
                }
            }catch (Exception e){
                return selfResponse.failure("挖机生产情况插入错误");
            }
        }
        return selfResponse.success("数据库修改成功");
    }
}
