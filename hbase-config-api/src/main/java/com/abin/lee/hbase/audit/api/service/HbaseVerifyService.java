package com.abin.lee.hbase.audit.api.service;

import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lee on 2019/1/11.
 * https://www.cnblogs.com/whyhappy/p/6568053.html
 */
@Service
public class HbaseVerifyService {
    @Resource
    HbaseTemplate hbaseTemplate;
//    @Autowired
//    HBaseConfiguration hbaseConfiguration ;


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
//        HConnection conn = HConnectionManager.createConnection(conf);
        // 创建一个数据库管理员
//        conn.getMaster().createTable()

//        conn.close();
    }


    // 添加一条数据
    public void addRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, Map<String, String> params)
            throws IOException {
        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
        // 建立一个数据库的连接
        // 通过rowkey创建一个put对象
        // 在put对象中设置列族、列、值
        hbaseTemplate.put(tableName, rowKey, columnFamily, "audit_driver_id", Bytes.toBytes(driverId));
        hbaseTemplate.put(tableName, rowKey, columnFamily, "audit_log_status", Bytes.toBytes(logStatus));
        hbaseTemplate.put(tableName, rowKey, columnFamily, "audit_log_type", Bytes.toBytes(logType));
        for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            hbaseTemplate.put(tableName, rowKey, columnFamily, entry.getKey(), Bytes.toBytes(entry.getValue()));
        }
//        hbaseTemplate.setAutoFlush(Boolean.TRUE);

    }

    // 通过rowkey获取一条数据
    public Map<String, Object> getRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp) throws IOException {
        Map<String, Object> resultMap = null;
        // 建立一个数据库的连接
        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
        // 获取表
//        hbaseTemplate.find(tableName, rowKey, columnFamily, "driverId", Bytes.toBytes(driverId));
        // 输出结果


//        Map<String, Object> resultMap = getRecordByCells(result);
//        // 关闭资源
//        table.close();
//        conn.close();
        return resultMap;
    }


}
