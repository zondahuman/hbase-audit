package com.abin.lee.hbase.dubbo.facade;

/**
 * Created by lee on 2019/2/20.
 */
public interface BusinessService {

    String doFlow(String name, int n);


    String doDegrade(String name, int n);



}