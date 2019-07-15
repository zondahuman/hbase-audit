package com.abin.lee.hbase.audit.api.controller;

import com.abin.lee.hbase.audit.api.entity.UserBean;
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

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2019/1/11.
 */
@Controller
@RequestMapping("/verify")
public class HbaseVerifyController {


    protected final static Logger logger = LoggerFactory.getLogger(HbaseVerifyController.class);
    protected final static Logger common = LoggerFactory.getLogger("common");

    //    @Autowired
//    HbaseAuditService hbaseAuditService;
    @Autowired
    HbaseVerifyService hbaseVerifyService;


    @RequestMapping(value = "/addRow", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String addRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, String params) {
        common.info("addRow--tableName=" + tableName + ",columnFamily=" + columnFamily + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",params=" + params);
        long start = System.currentTimeMillis();
        try {
            Map<String, String> paramsInput = JsonUtil.decodeJson(params, new TypeReference<Map<String, String>>() {
            });
            this.hbaseVerifyService.addRow(tableName, columnFamily, driverId, logStatus, logType, timeStamp, paramsInput);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        common.info("addRow--tableName=" + tableName + ",columnFamily=" + columnFamily + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",params=" + params +",cost="+ (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/insertRow", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String insertRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, String params) {
        logger.info("insert--tableName=" + tableName + ",columnFamily=" + columnFamily + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",params=" + params);
        long start = System.currentTimeMillis();
        try {
            Map<String, String> paramsInput = JsonUtil.decodeJson(params, new TypeReference<Map<String, String>>() {
            });
            this.hbaseVerifyService.insertRow(tableName, columnFamily, driverId, logStatus, logType, timeStamp, paramsInput);
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
        logger.info("get--tableName=" + tableName + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",timeStamp=" + timeStamp + ",cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<UserBean> findAll(String tableName, String columnFamily) {
        logger.info("findByCondition--tableName=" + tableName + ",columnFamily=" + columnFamily);
        long start = System.currentTimeMillis();
        List<UserBean> list = null;
        try {
            list = this.hbaseVerifyService.findAll(tableName, columnFamily);
        } catch (Exception e) {
            logger.error("e={}", e);
        }
        logger.info("findByCondition--tableName=" + tableName + ",columnFamily=" + columnFamily + ",cost=" + (System.currentTimeMillis() - start));
        return list;
    }


    @RequestMapping(value = "/findOne", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public UserBean findOne(String tableName, String rowName) {
        logger.info("findByCondition--tableName=" + tableName + ",rowName=" + rowName);
        long start = System.currentTimeMillis();
        UserBean bean = null;
        try {
            bean = this.hbaseVerifyService.findOne(tableName, rowName);
        } catch (Exception e) {
            logger.error("e={}", e);
        }
        logger.info("findByCondition--tableName=" + tableName + ",rowName=" + rowName + ",cost=" + (System.currentTimeMillis() - start));
        return bean;
    }


    @RequestMapping(value = "/findByCondition", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<UserBean> findByCondition(String tableName, String driverId, String logStatus, String logType, String startTimeStamp, String endTimeStamp) {
        logger.info("findByCondition--tableName=" + tableName + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",startTimeStamp=" + startTimeStamp + ",endTimeStamp=" + endTimeStamp);
        long start = System.currentTimeMillis();
        List<UserBean> list = null;
        try {
            list = this.hbaseVerifyService.findByCondition(tableName, driverId, logStatus, logType, startTimeStamp, endTimeStamp);
        } catch (Exception e) {
            logger.error("e={}", e);
        }
        logger.info("findByCondition--tableName=" + tableName + ",driverId=" + driverId + ",logStatus=" + logStatus + ",logType=" + logType + ",startTimeStamp=" + startTimeStamp + ",endTimeStamp=" + endTimeStamp + ",cost=" + (System.currentTimeMillis() - start));
        return list;
    }


}
