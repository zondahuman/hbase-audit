package com.abin.lee.hbase.audit.api.entity;

import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by lee on 2019/1/24.
 */
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class UserBean {

    private String auditDriverId;
    private String auditLogStatus;
    private String auditLogType;
    private String auditTimestamp;




    public UserBean(String auditDriverId, String auditLogStatus, String auditLogType, String auditTimestamp) {
        this.auditDriverId = auditDriverId;
        this.auditLogStatus = auditLogStatus;
        this.auditLogType = auditLogType;
        this.auditTimestamp = auditTimestamp;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("auditDriverId", auditDriverId)
                .append("auditLogStatus", auditLogStatus)
                .append("auditLogType", auditLogType)
                .append("auditTimestamp", auditTimestamp)
                .toString();
    }
}
