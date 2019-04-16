package com.abin.lee.hbase.audit.api.service;

import com.abin.lee.hbase.audit.api.constant.SentinelKey;
import com.abin.lee.hbase.audit.api.constant.SwitchKey;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by lee on 2019/2/2.
 */
@Slf4j
@Service
public class SentinelFlowService {


    public void callQps(String param) {
        Entry entry = null;
        try {
            entry = SphU.entry(SentinelKey.qpsKey);
            // token acquired, means pass
            log.info("callQps--pass--param=", param);
        } catch (BlockException e1) {
            log.error("callQps--nopass--param=" + param + " ,e1=" + e1);
        } catch (Exception e2) {
            // biz exception
            Tracer.trace(e2);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }


    public void callDegrade(String param) {

        Entry entry = null;
        try {
            entry = SphU.entry(SentinelKey.degradeKey);

            if (StringUtils.equals(SwitchKey.openKey, "OFF")) {
                throw new RuntimeException("----------------SWITCH---------OFF-----------------");
            }
            // Write your biz code here.
            log.info("callDegrade--pass--param=", param);
        } catch (BlockException e) {
            log.error("callDegrade--nopass--param=" + param + " ,e=" + e);
        } catch (Throwable t) {
            Tracer.trace(t);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }


}
