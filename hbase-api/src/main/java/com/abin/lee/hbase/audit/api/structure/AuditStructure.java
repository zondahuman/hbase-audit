package com.abin.lee.hbase.audit.api.structure;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by lee on 2019/1/27.
 */
public class AuditStructure {

    private String tableName = "SEC_TEST:AUDIT_TEST";

    public static byte[] columnFamily = Bytes.toBytes("cf");

    public static byte[] auditDriverId = Bytes.toBytes("auditDriverId");
    public static byte[] auditLogStatus = Bytes.toBytes("auditLogStatus");
    public static byte[] auditLogType = Bytes.toBytes("auditLogType");
    public static byte[] auditTimestamp = Bytes.toBytes("auditTimestamp");


    public static String audit_driver_id = "auditDriverId" ;
    public static String audit_log_status =  "auditLogStatus" ;
    public static String audit_log_type =  "auditLogType" ;
    public static String audit_timestamp =  "auditTimestamp" ;


}
