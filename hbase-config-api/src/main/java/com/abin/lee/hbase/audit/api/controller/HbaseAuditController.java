package com.abin.lee.hbase.audit.api.controller;

import com.abin.lee.hbase.audit.api.service.HbaseVerifyService;
import com.abin.lee.hbase.audit.common.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by lee on 2019/1/11.
 */
@Controller
@RequestMapping("/audit")
public class HbaseAuditController {


    protected final static Logger logger = LoggerFactory.getLogger(HbaseAuditController.class);

    //    @Autowired
//    HbaseAuditService hbaseAuditService;
    @Autowired
    HbaseVerifyService hbaseVerifyService;


    @RequestMapping(value = "/insert", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String insert(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, String params) {
        logger.info("insert--tableName=" + tableName + ",columnFamily=" + columnFamily + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",params=" + params);
        long start = System.currentTimeMillis();
        try {
            Map<String, String> paramsInput = JsonUtil.decodeJson(params, new TypeReference<Map<String, String>>() {
            });
            this.hbaseVerifyService.addRow(tableName, columnFamily, driverId, logStatus, logType, timeStamp, paramsInput);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("insert--tableName=" + tableName + ",columnFamily=" + columnFamily + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",params=" + params + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String get(String tableName, String driverId, String logStatus, String logType, long timeStamp) {
        logger.info("get--tableName=" + tableName + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp);
        long start = System.currentTimeMillis();
        try {
//            this.hbaseVerifyService.getRow(tableName, driverId, logStatus, logType, timeStamp);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("get--tableName=" + tableName + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


}
