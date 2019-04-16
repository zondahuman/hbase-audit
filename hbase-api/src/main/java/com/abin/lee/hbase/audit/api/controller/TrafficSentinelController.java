package com.abin.lee.hbase.audit.api.controller;

import com.abin.lee.hbase.audit.api.constant.SwitchKey;
import com.abin.lee.hbase.audit.api.service.SentinelFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lee on 2019/1/28.
 */
@Controller
@RequestMapping("/sentinel")
public class TrafficSentinelController {

    protected final static Logger logger = LoggerFactory.getLogger(TrafficSentinelController.class);

    @Resource
    SentinelFlowService sentinelFlowService;


    @RequestMapping(value = "/callQps", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String callQps(String params) {
        logger.info("callQps--params=" + params);
        long start = System.currentTimeMillis();
        try {
            sentinelFlowService.callQps(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("callQps--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/callQpsBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String callQpsBatch(String params) {
        logger.info("callQpsBatch--params=" + params);
        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < 100000; i++) {
                sentinelFlowService.callQps(params);
            }
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("callQpsBatch--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/callDegrade", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String callDegrade(String params) {
        logger.info("callDegrade--params=" + params);
        long start = System.currentTimeMillis();
        try {
            sentinelFlowService.callDegrade(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("callDegrade--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/callDegradeBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String callDegradeBatch(String params, Integer total, Integer sleepTime) {
        logger.info("callDegradeBatch--params=" + params);
        long start = System.currentTimeMillis();
        try {
            Integer count = 0;
            for (int i = 0; i < total; i++) {
                count = count + 1;
                sentinelFlowService.callDegrade(params);
                Integer qps = SwitchKey.limitKey;
                if (count % qps == 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.getStackTrace();

                    }
                }
            }

        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("callDegradeBatch--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/putOpenKey", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String putOpenKey(String params) {
        logger.info("putOpenKey--params=" + params);
        long start = System.currentTimeMillis();
        String result = "";
        try {
            result = SwitchKey.putOpenKey(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("putOpenKey--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return result;
    }


    @RequestMapping(value = "/putLimitKey", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String putLimitKey(Integer params) {
        logger.info("putLimitKey--params=" + params);
        long start = System.currentTimeMillis();
        String result = "";
        try {
            result = SwitchKey.putLimitKey(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("putLimitKey--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return result;
    }


}
