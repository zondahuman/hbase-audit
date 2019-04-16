package com.abin.lee.hbase.audit.api.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * Created by Administrator on 2017/4/10.
 * https://blog.csdn.net/com3294/article/details/79104702
 */
//@Configuration
//public class HbaseConfig {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(HbaseConfig.class);
////    @Value("${hbase.zookeeper.quorum}")
////    private String quorum;
////    @Value("${hbase.zookeeper.clientPort}")
////    private String clientPort;
//
//
//    @Bean
//    public HbaseTemplate hbaseTemplate(@Value("${hbase.zookeeper.quorum}") String quorum,
//                                       @Value("${hbase.zookeeper.clientPort}") String port) {
//        HbaseTemplate hbaseTemplate = new HbaseTemplate();
//        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", quorum);
//        conf.set("hbase.zookeeper.port", port);
//        hbaseTemplate.setConfiguration(conf);
//        hbaseTemplate.setAutoFlush(true);
//        return hbaseTemplate;
//    }
//
//
//}
