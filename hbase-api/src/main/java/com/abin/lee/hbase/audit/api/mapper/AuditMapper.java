package com.abin.lee.hbase.audit.api.mapper;


import com.abin.lee.hbase.audit.api.entity.UserBean;
import com.abin.lee.hbase.audit.api.structure.AuditStructure;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;

/**
 * Created by lee on 2019/1/27.
 */
public class AuditMapper implements RowMapper<UserBean> {

    @Override
    public UserBean mapRow(Result result, int rowNum) throws Exception {
        return new UserBean(Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditDriverId)),
                Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogStatus)),
                Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditLogType)),
                Bytes.toString(result.getValue(AuditStructure.columnFamily, AuditStructure.auditTimestamp)));
    }


}
