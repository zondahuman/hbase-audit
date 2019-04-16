//package com.abin.lee.hbase.audit.api.test;
//
//import com.abin.lee.hbase.audit.api.service.HbaseAuditService;
//import com.google.common.collect.Maps;
//import com.google.common.primitives.Longs;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * Created by lee on 2019/1/11.
// */
//public class HbaseCreateTableTest {
//
//    public static void main(String[] args) throws IOException {
//        HbaseAuditService hbaseService = new HbaseAuditService();
//        String tableName = "SEC_TEST:AUDIT_TEST";
//        String[] columnFamilys = new String[]{"SEC_TEST:AUDIT_TEST"};
//        hbaseService.createTable(tableName, columnFamilys);
//    }
//
//
//    @Test
//    public void testPutColumn() throws IOException {
//        HbaseAuditService hbaseService = new HbaseAuditService();
//        String tableName = "SEC_TEST:AUDIT_TEST";
//        String columnFamily = "cf";
//        String driverId = "580543123787229";
//        String logStatus = "1";
//        String logType = "2";
//        long timeStamp = System.currentTimeMillis();
//        Map<String, String> params = Maps.newHashMap();
//        params.put("audit_timestamp", timeStamp + "");
//        hbaseService.addRow(tableName, columnFamily, driverId, logStatus, logType, timeStamp, params);
//
//
//        System.out.println(System.getenv("HADOOP_HOME"));
//    }
//
//
//    @Test
//    public void testGetColumn() throws IOException {
//        HbaseAuditService hbaseService = new HbaseAuditService();
//        String tableName = "SEC_TEST:AUDIT_TEST";
//        String columnFamily = "cf";
//        String driverId = "580543123787229";
//        String logStatus = "1";
//        String logType = "2";
//        long timeStamp = Longs.tryParse("1548142494025");
//        Map<String, String> params = Maps.newHashMap();
//        params.put("audit_timestamp", timeStamp + "");
//        Map<String, Object> resultMap = hbaseService.getRow(tableName, driverId, logStatus, logType, timeStamp);
//        System.out.println("resultMap=" + resultMap);
//    }
//
//
//}
