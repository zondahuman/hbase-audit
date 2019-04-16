package com.abin.lee.hbase.audit.limit.test;


import com.abin.lee.hbase.audit.common.util.HttpClientUtil;
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

/**
 * Created by lee on 2018/10/15.
 */
public class SetLimitTest {
    private static final String httpUrl = "http://localhost:8111/traffic/setLimit";


    @Test
    public void testTrafficLimit() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String params = "3";
//            String params = (int)(Math.random()*10000)+"";

            nvps.add(new BasicNameValuePair("params", params));


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


    @Test
    public void test() throws InterruptedException {
        int i = 0;
        for (int temp = 0; temp < 1000; temp++) {
            i++;
            if (i % 300 == 0) {
                Thread.sleep(1000L);
            }
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            testTrafficLimit();
                        }
                    }
            ).start();
        }

    }


}
