package com.gradproject.server.service;

import com.gradproject.server.dao.DiggerConfigMapper;
import com.gradproject.server.dao.ReWajiConfigMapper;
import com.gradproject.server.entity.DiggerConfig;
import com.gradproject.server.entity.ReWajiConfig;
import com.gradproject.server.entity.model.SelfResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public SelfResponse editDiggerConfig(ReWajiConfig reWajiConfig) {
        SelfResponse selfResponse=new SelfResponse();
        try {
            int i=reWajiConfigMapper.updateByPrimaryKey(reWajiConfig);
            log.info("数据更新结果为：【{}】",i);
            return selfResponse.success();
        }catch (Exception e){
            log.error("数据更新异常，异常信息为：【{}】",e.getMessage(),e);
        }
        return selfResponse.failure("数据更新异常");
    }
}
