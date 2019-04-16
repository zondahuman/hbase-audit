//package com.abin.lee.hbase.audit.api.service;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Maps;
//import org.apache.commons.codec.binary.StringUtils;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.CellUtil;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by lee on 2019/1/11.
// * https://www.cnblogs.com/whyhappy/p/6568053.html
// */
//@Service
//public class HbaseAuditService {
//    public Configuration conf = null;
//
//    {
////        conf = HBaseConfiguration.create();
//        conf = new Configuration();
//        conf.set("hbase.zookeeper.quorum", "10.179.89.106");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//    }
//
//
//    public static String getRowKey(String driverId, String logStatus, String logType, long timestamp) {
//        Preconditions.checkNotNull(driverId, "driverId 不能为空");
//        Preconditions.checkArgument(StringUtils.equals(logStatus, "1") || StringUtils.equals(logStatus, "2"), "状态只能为1, 2");
//        Preconditions.checkArgument(StringUtils.equals(logStatus, "1") || StringUtils.equals(logStatus, "2"), "类型只能为1, 2");
//        Preconditions.checkArgument(Long.valueOf(timestamp).toString().length() == 13, "时间戳长度必须为13");
//
////        String salt = MD5Hash.getMD5AsHex(Bytes.toBytes(driverId)).substring(0, 5);
//        long ts = Long.MAX_VALUE / 1000000 - timestamp;
//
//        return driverId + logStatus + logType + ts;
//
//    }
//
//    // 创建数据库表
//    public void createTable(String tableName, String[] columnFamilys) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 创建一个数据库管理员
////        conn.getMaster().createTable()
//
//        conn.close();
//    }
//
//
//    // 添加一条数据
//    public void addRow(String tableName, String columnFamily, String driverId, String logStatus, String logType, long timeStamp, Map<String, String> params)
//            throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
//        // 通过rowkey创建一个put对象
//        Put put = new Put(Bytes.toBytes(rowKey));
//        // 在put对象中设置列族、列、值
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("audit_driver_id"), Bytes.toBytes(driverId));
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("audit_log_status"), Bytes.toBytes(logStatus));
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes("audit_log_type"), Bytes.toBytes(logType));
//        for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
//            Map.Entry<String, String> entry = iterator.next();
//            put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
//        }
//        // 插入数据,可通过put(List<Put>)批量插入
//        table.put(put);
//        // 关闭资源
//        table.close();
//        conn.close();
//    }
//
//    // 通过rowkey获取一条数据
//    public Map<String, Object> getRow(String tableName, String driverId, String logStatus, String logType, long timeStamp) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        String rowKey = getRowKey(driverId, logStatus, logType, timeStamp);
//        // 通过rowkey创建一个get对象
//        Get get = new Get(Bytes.toBytes(rowKey));
//        // 输出结果
//        Result result = table.get(get);
//
//        Map<String, Object> resultMap = getRecordByCells(result);
//        // 关闭资源
//        table.close();
//        conn.close();
//        return resultMap;
//    }
//
//    private static Map<String, Object> getRecordByCells(Result rs) {
//
//        Map<String, Object> resMap = Maps.newHashMap();
//
//        for (Cell cell : rs.rawCells()) {
//            String columnName = new String(CellUtil.cloneQualifier(cell));
//            String columnValue = new String(CellUtil.cloneValue(cell));
//
//            resMap.put(columnName, columnValue);
//        }
//
//        return resMap;
//    }
//
//
//    // 全表扫描
//    public void scanTable(String tableName) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 创建一个扫描对象
//        Scan scan = new Scan();
//        // 扫描全表输出结果
//        ResultScanner results = table.getScanner(scan);
//        for (Result result : results) {
//            for (Cell cell : result.rawCells()) {
//                System.out.println(
//                        "行键:" + new String(CellUtil.cloneRow(cell)) + "\t" +
//                                "列族:" + new String(CellUtil.cloneFamily(cell)) + "\t" +
//                                "列名:" + new String(CellUtil.cloneQualifier(cell)) + "\t" +
//                                "值:" + new String(CellUtil.cloneValue(cell)) + "\t" +
//                                "时间戳:" + cell.getTimestamp());
//            }
//        }
//        // 关闭资源
//        results.close();
//        table.close();
//        conn.close();
//    }
//
//
//    // 删除一条数据
//    public void delRow(String tableName, String rowKey) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 删除数据
//        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        table.delete(delete);
//        // 关闭资源
//        table.close();
//        conn.close();
//    }
//
//
//    // 删除多条数据
//    public void delRows(String tableName, String[] rows) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 删除多条数据
//        List<Delete> list = new ArrayList<Delete>();
//        for (String row : rows) {
//            Delete delete = new Delete(Bytes.toBytes(row));
//            list.add(delete);
//        }
//        table.delete(list);
//        // 关闭资源
//        table.close();
//        conn.close();
//    }
//
//    // 删除列族
//    public void delColumnFamily(String tableName, String columnFamily) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 创建一个数据库管理员
//        HBaseAdmin hAdmin = (HBaseAdmin) conn.getMaster();
//        // 删除一个表的指定列族
//        hAdmin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(columnFamily));
//        // 关闭资源
//        conn.close();
//    }
//
//    // 删除数据库表
//    public void deleteTable(String tableName) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 创建一个数据库管理员
//        HBaseAdmin hAdmin = (HBaseAdmin) conn.getMaster();
//        if (hAdmin.tableExists(TableName.valueOf(tableName))) {
//            // 失效表
//            hAdmin.disableTable(TableName.valueOf(tableName));
//            // 删除表
//            hAdmin.deleteTable(TableName.valueOf(tableName));
//            System.out.println("删除" + tableName + "表成功");
//            conn.close();
//        } else {
//            System.out.println("需要删除的" + tableName + "表不存在");
//            conn.close();
//            System.exit(0);
//        }
//    }
//
//    // 追加插入(将原有value的后面追加新的value，如原有value=a追加value=bc则最后的value=abc)
//    public void appendData(String tableName, String rowKey, String columnFamily, String column, String value)
//            throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 通过rowkey创建一个append对象
//        Append append = new Append(Bytes.toBytes(rowKey));
//        // 在append对象中设置列族、列、值
//        append.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
//        // 追加数据
//        table.append(append);
//        // 关闭资源
//        table.close();
//        conn.close();
//    }
//
//    // 符合条件后添加数据(只能针对某一个rowkey进行原子操作)
//    public boolean checkAndPut(String tableName, String rowKey, String columnFamilyCheck, String columnCheck, String valueCheck, String columnFamily, String column, String value) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 设置需要添加的数据
//        Put put = new Put(Bytes.toBytes(rowKey));
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
//        // 当判断条件为真时添加数据
//        boolean result = table.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamilyCheck),
//                Bytes.toBytes(columnCheck), Bytes.toBytes(valueCheck), put);
//        // 关闭资源
//        table.close();
//        conn.close();
//
//        return result;
//    }
//
//    // 符合条件后刪除数据(只能针对某一个rowkey进行原子操作)
//    public boolean checkAndDelete(String tableName, String rowKey, String columnFamilyCheck, String columnCheck,
//                                  String valueCheck, String columnFamily, String column) throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 设置需要刪除的delete对象
//        Delete delete = new Delete(Bytes.toBytes(rowKey));
//        delete.deleteColumn(Bytes.toBytes(columnFamilyCheck), Bytes.toBytes(columnCheck));
//        // 当判断条件为真时添加数据
//        boolean result = table.checkAndDelete(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamilyCheck), Bytes.toBytes(columnCheck),
//                Bytes.toBytes(valueCheck), delete);
//        // 关闭资源
//        table.close();
//        conn.close();
//
//        return result;
//    }
//
//
//    // 计数器(amount为正数则计数器加，为负数则计数器减，为0则获取当前计数器的值)
//    public long incrementColumnValue(String tableName, String rowKey, String columnFamily, String column, long amount)
//            throws IOException {
//        // 建立一个数据库的连接
//        HConnection conn = HConnectionManager.createConnection(conf);
//        // 获取表
//        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
//        // 计数器
//        long result = table.incrementColumnValue(Bytes.toBytes(rowKey), Bytes.toBytes(columnFamily), Bytes.toBytes(column), amount);
//        // 关闭资源
//        table.close();
//        conn.close();
//
//        return result;
//    }
//
//
//}
