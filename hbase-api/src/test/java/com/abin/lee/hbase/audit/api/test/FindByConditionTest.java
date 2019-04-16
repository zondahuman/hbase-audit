package com.abin.lee.hbase.audit.api.test;


import com.abin.lee.hbase.audit.common.util.DateUtil;
import com.abin.lee.hbase.audit.common.util.HttpClientUtil;
import com.abin.lee.hbase.audit.common.util.JsonUtil;
import com.google.common.collect.Maps;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2018/10/15.
 */
public class FindByConditionTest {
    private static final String httpUrl = "http://localhost:8111/audit/findByCondition";


    @Test
    public void testFindByCondition() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String tableName = "SEC_TEST:AUDIT_TEST";
            String columnFamily = "cf";
            String driverId = "1234567890";
//            String driverId = (int)(Math.random()*10000)+"";
            String logStatus = "1";
            String logType = "2";
//            String startTimeStamp = DateUtil.getSomeDate(-20);
            String startTimeStamp = DateUtil.getYMDHMSTime();
            String endTimeStamp = DateUtil.getSomeDate(-20);

            nvps.add(new BasicNameValuePair("tableName", tableName));
            nvps.add(new BasicNameValuePair("columnFamily", columnFamily));
            nvps.add(new BasicNameValuePair("driverId", driverId));
            nvps.add(new BasicNameValuePair("logStatus", logStatus));
            nvps.add(new BasicNameValuePair("logType", logType));//返回有参数的
            nvps.add(new BasicNameValuePair("startTimeStamp", startTimeStamp + ""));
            nvps.add(new BasicNameValuePair("endTimeStamp", endTimeStamp));

            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
