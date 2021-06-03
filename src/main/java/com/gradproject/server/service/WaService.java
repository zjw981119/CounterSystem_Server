package com.gradproject.server.service;

import com.gradproject.server.dao.WaMapper;
import com.gradproject.server.entity.Waing;
import com.gradproject.server.entity.model.SelfResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


        List<Waing> waings =new ArrayList<>();
        List<Waing> wings = waMapper.selectWa(value1, value2);
        List<Waing> aings = waMapper.selectW(value1, value2);
        SelfResponse response = new SelfResponse();
        for (Waing wing : wings) {
            waings.add(wing);
            Waing temp = new Waing();
            int i = 0;
            float a = 0;
            float b = 0;
            float c = 0;
            float d = 0;
            float e = 0;
            float f = 0;
            float g = 0;
            float h = 0;
            i += Integer.parseInt(wing.getTripNum());
            a += Float.parseFloat(wing.getBiao());
            b += Float.parseFloat(wing.getJishi());
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
                    i += Integer.parseInt(aing.getTripNum());
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
