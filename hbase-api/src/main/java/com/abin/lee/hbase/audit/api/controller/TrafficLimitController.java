package com.abin.lee.hbase.audit.api.controller;

import com.abin.lee.hbase.audit.api.limit.TrafficLimitService;
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
@RequestMapping("/traffic")
public class TrafficLimitController {

    protected final static Logger logger = LoggerFactory.getLogger(TrafficLimitController.class);

    @Resource
    TrafficLimitService trafficLimitService;


    @RequestMapping(value = "/limiter", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String limiter(String params) {
        logger.info("limiter--params=" + params);
        long start = System.currentTimeMillis();
        try {
            if (trafficLimitService.tryAcquire(1)) {
                System.out.println("call--Processed.................");
            } else {
                System.out.println("over--exceeed-------------------");
            }
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("limiter--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }

    @RequestMapping(value = "/getlimiter", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String getlimiter(String params) {
        logger.info("getlimiter--params=" + params);
        long start = System.currentTimeMillis();
        boolean flag = Boolean.FALSE;
        try {
            flag = trafficLimitService.tryAcquire(1);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("getlimiter--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return flag + "";
    }

    @RequestMapping(value = "/setLimit", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String setLimit(String params) {
        logger.info("setLimit--params=" + params);
        long start = System.currentTimeMillis();
        try {
            trafficLimitService.setLimit(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("setLimit--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


    @RequestMapping(value = "/getLimit", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Double getLimit(String params) {
        logger.info("getLimit--params=" + params);
        long start = System.currentTimeMillis();
        Double result = null;
        try {
            result = trafficLimitService.getLimit();
        } catch (Exception e) {
            logger.error("e={}", e);
        }
        logger.info("getLimit--params=" + params + ",result=" + result + "，cost=" + (System.currentTimeMillis() - start));
        return result;
    }


    @RequestMapping(value = "/setRate", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String setRate(String params) {
        logger.info("setRate--params=" + params);
        long start = System.currentTimeMillis();
        try {
            trafficLimitService.setRate(params);
        } catch (Exception e) {
            logger.error("e={}", e);
            return "FAILURE";
        }
        logger.info("setRate--params=" + params + "，cost=" + (System.currentTimeMillis() - start));
        return "SUCCESS";
    }


}
