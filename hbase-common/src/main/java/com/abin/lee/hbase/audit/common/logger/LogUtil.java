package com.abin.lee.hbase.audit.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lee on 2018/10/23.
 */
public class LogUtil {

    private static final Logger audit = LoggerFactory.getLogger("audit");


    /**
     *
     * @param log
     */
    public static void auditInfo(String log) {
        audit.info("||"+log);
    }








    public static void main(String[] args) {


    }



}
