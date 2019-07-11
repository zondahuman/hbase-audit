package com.abin.lee.hbase.audit.api.service;

import com.abin.lee.hbase.audit.api.entity.UserBean;
import com.abin.lee.hbase.audit.api.mapper.AuditMapper;
import com.abin.lee.hbase.audit.api.structure.AuditStructure;
import com.abin.lee.hbase.audit.common.logger.LogUtil;
import com.abin.lee.hbase.audit.common.util.DateUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2019/1/11.
 * https://www.cnblogs.com/whyhappy/p/6568053.html
 */
@Service
public class UserAuditService {

    @Resource
    HbaseTemplate hbaseTemplate;


    public static String getRowKey(String driverId, String logStatus, String logType, long timestamp) {
        Preconditions.checkNotNull(driverId, "driverId 不能为空");
        Preconditions.checkArgument(StringUtils.equals(logStatus, "1") || StringUtils.equals(logStatus, "2"), "状态只能为1, 2");
        Preconditions.checkArgument(StringUtils.equals(logStatus, "1") || StringUtils.equals(logStatus, "2"), "类型只能为1, 2");
        Preconditions.checkArgument(Long.valueOf(timestamp).toString().length() == 13, "时间戳长度必须为13");

//        String salt = MD5Hash.getMD5AsHex(Bytes.toBytes(driverId)).substring(0, 5);
        long ts = Long.MAX_VALUE / 1000000 - timestamp;

        return driverId + logStatus + logType + ts;

    }

    // 创建数据库表
    public void createTable(String tableName, String[] columnFamilys) throws IOException {
        // 建立一个数据库的连接
        Configuration configuration = HBaseConfiguration.create();
        hbaseTemplate.getTableFactory().createHTableInterface(configuration, Bytes.toBytes(tableName));
    }


    // 添加一条数据
    public void addRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, Map<String, String> params)
            throws IOException {
        LogUtil.auditInfo("tableName="+tableName+"||columnFamily="+columnFamily+"||driverId="+driverId+"||logStatus="+logStatus+"||logType="+logType+"||timeStamp="+timeStamp+"||params="+params);
        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
        // 建立一个数据库的连接
        // 通过rowkey创建一个put对象
        // 在put对象中设置列族、列、值
        hbaseTemplate.put(tableName, rowKey, columnFamily, AuditStructure.audit_driver_id, Bytes.toBytes(driverId));
        hbaseTemplate.put(tableName, rowKey, columnFamily, AuditStructure.audit_log_status, Bytes.toBytes(logStatus));
        hbaseTemplate.put(tableName, rowKey, columnFamily, AuditStructure.audit_log_type, Bytes.toBytes(logType));
        for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            hbaseTemplate.put(tableName, rowKey, columnFamily, entry.getKey(), Bytes.toBytes(entry.getValue()));
        }

    }

    // 添加一条数据
    public Boolean insertRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, Map<String, String> params)
            throws IOException {
        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
        return hbaseTemplate.execute(tableName, (hbaseTableInterface) -> {
            boolean flag = Boolean.FALSE;
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(AuditStructure.columnFamily, AuditStructure.auditDriverId, Bytes.toBytes(driverId));
            put.add(AuditStructure.columnFamily, AuditStructure.auditLogStatus, Bytes.toBytes(logStatus));
            put.add(AuditStructure.columnFamily, AuditStructure.auditLogType, Bytes.toBytes(logType));
            for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, String> entry = iterator.next();
                put.add(AuditStructure.columnFamily, Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
            }
            hbaseTableInterface.put(put);
            flag = Boolean.TRUE;
            return flag;
        });

    }

    // 获取所有的记录
    public List<UserBean> findAll(String tableName, String columnFamily) throws IOException {

        return hbaseTemplate.find(tableName, columnFamily, new RowMapper<UserBean>() {
            @Override
            public UserBean mapRow(Result result, int rowNum) throws Exception {
                return new UserBean(Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditDriverId)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogStatus)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogType)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditTimestamp)));
            }
        });
    }


    // 通过rowkey获取一条数据
    public UserBean findOne(String tableName, String rowName) throws IOException {

        return hbaseTemplate.get(tableName, rowName, new RowMapper<UserBean>() {
            @Override
            public UserBean mapRow(Result result, int rowNum) throws Exception {
                return new UserBean(Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditDriverId)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogStatus)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogType)),
                        Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditTimestamp)));
            }
        });
    }


    //根据条件查询
    public List<UserBean> findByCondition(String tableName, String driverId, String logStatus, String logType, String startTimeStamp, String endTimeStamp) throws IOException {
        byte[] startRow = Bytes.toBytes(getRowKey(driverId, logStatus, logType, DateUtil.getYMDHMSTime(endTimeStamp).getTime()));
        byte[] stopRow = Bytes.toBytes(getRowKey(driverId, logStatus, logType, DateUtil.getYMDHMSTime(startTimeStamp).getTime()));
        Scan scan = new Scan(startRow, stopRow);
        return hbaseTemplate.find(tableName, scan, new AuditMapper());
    }





}
