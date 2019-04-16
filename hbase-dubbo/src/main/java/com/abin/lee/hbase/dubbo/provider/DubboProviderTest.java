package com.abin.lee.hbase.dubbo.provider;


import com.abin.lee.hbase.dubbo.facade.BusinessService;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;
import org.junit.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 运行该用例的时候需要加入JVM参数：-Djava.net.preferIPv4Stack=true，
 * 否则可能会抛出{@code java.lang.IllegalStateException: Can't assign requested address}
 */
public class DubboProviderTest {

    private static final String resource = "com.abin.lee.hbase.dubbo.facade.DemoService:sayHello(java.lang.String,int)";

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring-dubbo-provider-filter.xml"});
        context.start();
        BusinessService businessService = (BusinessService) context.getBean("businessService");
        String result = businessService.doFlow("Test dubbo provider filter", 1);
        System.out.println("result=" + result);
        ClusterNode node = ClusterBuilderSlot.getClusterNode(resource);
        Thread.sleep(1000 * 60 * 100);
        Assert.assertNotNull(node);
    }







}
