package com.abin.lee.hbase.audit.api.service;

import com.abin.lee.hbase.audit.api.constant.SentinelKey;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2019/2/2.
 */
@Slf4j
@Service
public class TrafficRestrictionService {

    public void callQps(String param){
        Entry entry = null;
        try {
            entry = SphU.entry(SentinelKey.qpsKey);
            // token acquired, means pass

        } catch (BlockException e1) {
            log.error("callQps--param=",param);
        } catch (Exception e2) {
            // biz exception
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }




}
