package com.abin.lee.hbase.dubbo.facade.impl;


import com.abin.lee.hbase.dubbo.facade.BusinessService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lee on 2019/2/20.
 */
@Slf4j
public class BusinessServiceImpl implements BusinessService {

    public String doFlow(String name, int n) {
        log.info("doFlow----name=" + name + " ,n=" + n);
//        System.out.println("name="+name+" ,n="+n);
        String result = "doFlow----Hello " + name + ", " + n;
        return result;
    }

    @Override
    public String doDegrade(String name, int n) {
        log.info("doDegrade---name=" + name + " ,n=" + n);
//        System.out.println("name="+name+" ,n="+n);
        if(n<=1000) {
            throw new RuntimeException("Already Degrade Guys");
        }
        String result = "doDegrade----Hello " + name + ", " + n;
        return result;
    }


}
