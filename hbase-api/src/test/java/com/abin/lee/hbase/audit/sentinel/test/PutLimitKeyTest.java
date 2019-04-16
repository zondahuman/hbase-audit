package com.abin.lee.hbase.audit.sentinel.test;


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
import java.util.concurrent.*;

/**
 * Created by lee on 2018/10/15.
 */
public class PutLimitKeyTest {
    private static final String httpUrl = "http://localhost:8111/sentinel/putLimitKey";


            @Test
    public void testPutLimitKey() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String params =  "2";
//            String params =  "10";

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
    public void testLimit() {
        int i = 0;
        for (int temp = 0; temp < 100; temp++) {
//            i++;
//            if (i % 300 == 0) {
//                Thread.sleep(1000L);
//            }
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("-----thread-start----------------");
                            testPutLimitKey();
                            System.out.println("-----thread-end----------------");
                        }
                    }
            ).start();
        }

    }



    public String concurrencyCallDegrade() {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            HttpPost httpPost = new HttpPost(httpUrl);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String params = (int) (Math.random() * 10000) + "";

            nvps.add(new BasicNameValuePair("params", params));

            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
//            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result ;
    }


    @Test
    public void testConcurrencyCallQpsBatch() throws ExecutionException, InterruptedException {
        Integer total = 2000;
        ExecutorService exc = Executors.newFixedThreadPool(total);
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < total; i++) {
            //提交单个线程
            Future<String> future = exc.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return concurrencyCallDegrade();
                }
            });
            //将每个线程放入线程集合， 这里如果任何一个线程的执行结果没有回调，线程都会自动堵塞
            futures.add(future);
        }
        for (Future<String> future : futures) {
            String json = future.get();
            System.out.println("json=" + json);
        }
        //关闭线程池
        exc.shutdown();
    }



    public static void main(String[] args) {
        int i = 0;
        for (int temp = 0; temp < 100; temp++) {
//            i++;
//            if (i % 300 == 0) {
//                Thread.sleep(1000L);
//            }
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("-----thread-start----------------");
//                            concurrencyCallQps();
                            System.out.println("-----thread-end----------------");
                        }
                    }
            ).start();
        }

    }


}
