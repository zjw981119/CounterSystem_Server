package com.gradproject.server.service;

import com.alibaba.druid.stat.JdbcConnectionStat;
import com.gradproject.server.dao.WaMapper;
import com.gradproject.server.entity.Waing;
import com.gradproject.server.entity.model.SelfResponse;
import com.gradproject.server.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Float.valueOf;

@Service
@Log4j2
public class WaService {
    @Resource
    private WaMapper waMapper;
    public SelfResponse selectWa(
                                String value1,
                                String value2
                                ){

        Date startTime;
        Date endTime;

        List<Waing> waings =new ArrayList<>();

        SelfResponse response = new SelfResponse();
//        try {
//            startTime = DateUtils.getDate(value1, "yyyy-MM-dd HH:mm:ss");
//            endTime = DateUtils.getDate(value2, "yyyy-MM-dd HH:mm:ss");
//            log.info("转化后的数据startTime:{},endTime:{}", startTime, endTime);
//        } catch (Exception e) {
//            log.info("时间数据格式化错误：{},{}", e.getMessage(), e);
//            return response.error("格式转化错误");
//        }
        List<Waing> wings = waMapper.selectWa(value1, value2);
        List<Waing> aings = waMapper.selectW(value1, value2);
        for (Waing wing : wings) {
            waings.add(wing);
            Waing temp = new Waing();
            float i = 0;
            float a = 0;
            float b = 0;
            float c = 0;
            float d = 0;
            float e = 0;
            float f = 0;
            float g = 0;
            float h = 0;
            if (null != wing.getTripNum()) {
                i += Float.parseFloat(wing.getTripNum());
            }
            if (null != wing.getBiao()) {
                a += Float.parseFloat(wing.getBiao());
            }
            if (null != wing.getJishi()) {
                b += Float.parseFloat(wing.getJishi());
            }


            if (null != wing.getMao()) {
                c += Float.parseFloat(wing.getMao());
            }
            if (null != wing.getZhuang()) {
                d += Float.parseFloat(wing.getZhuang());
            }
            if (null != wing.getMei()) {
                e += Float.parseFloat(wing.getMei());
            }
            if (null != wing.getOil_L()) {
                f += Float.parseFloat(wing.getOil_L());
            }
            if (null != wing.getRan()) {
                g += Float.parseFloat(wing.getRan());
            }
            if (null != wing.getFang()) {
                h += Float.parseFloat(wing.getFang());
            }
            for (Waing aing : aings) {
                if (aing.getBind_excavator() .equals(wing.getCar_no()) ){
                    waings.add(aing);
                    if( null != aing.getTripNum() ) {
                        i +=  Float.parseFloat(aing.getTripNum());
                    }
                    if( null != aing.getBiao() ) {
                        a +=  Float.parseFloat(aing.getBiao());
                    }
                    if( null != aing.getJishi() ) {
                        b +=  Float.parseFloat(aing.getJishi());
                    }
                    if( null != aing.getMao() ) {
                        c +=  Float.parseFloat(aing.getMao());
                    }
                    if( null != aing.getZhuang() ) {
                        d +=  Float.parseFloat(aing.getZhuang());
                    }

                    if( null != aing.getMei() ) {
                        e +=  Float.parseFloat(aing.getMei());
                    }
                    if( null != aing.getOil_L() ) {
                        f +=  Float.parseFloat(aing.getOil_L());
                    }
                    if( null != aing.getRan() ) {
                        g +=  Float.parseFloat(aing.getRan());
                    }
                    if (null != aing.getFang()){
                        h +=  Float.parseFloat(aing.getFang());
                    }
                }
            }
            temp.setTripNum(String.valueOf(i));
            temp.setBiao(String.valueOf(a));
            temp.setJishi(String.valueOf(b));
            temp.setMao(String.valueOf(c));
            temp.setZhuang(String.valueOf(d));
            temp.setMei(String.valueOf(e));
            temp.setOil_L(String.valueOf(f));
            temp.setRan(String.valueOf(g));
            temp.setFang(String.valueOf(h));
            temp.setCar_type("合计");
            waings.add(temp);
        }
        System.out.println(waings);
        return response.success(waings);


    }

}
