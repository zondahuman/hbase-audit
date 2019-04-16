package com.abin.lee.hbase.audit.api.test;


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
public class FindOneTest {
    private static final String httpUrl = "http://localhost:8111/audit/findOne";


    @Test
    public void testFindOne() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String tableName = "SEC_TEST:AUDIT_TEST";

            String rowName = "1234567890127674774650824";

            nvps.add(new BasicNameValuePair("tableName", tableName));
            nvps.add(new BasicNameValuePair("rowName", rowName));


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
