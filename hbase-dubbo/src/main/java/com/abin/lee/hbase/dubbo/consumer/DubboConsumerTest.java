package com.abin.lee.hbase.dubbo.consumer;


import com.abin.lee.hbase.dubbo.facade.BusinessService;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by lee on 2019/2/20.
 */

/**
 * 运行该用例的时候需要加入JVM参数：-Djava.net.preferIPv4Stack=true，
 * 否则可能会抛出{@code java.lang.IllegalStateException: Can't assign requested address}
 *
 * @author leyou
 */
@Slf4j
public class DubboConsumerTest {
    private static final String resource = "com.abin.lee.hbase.dubbo.facade.DemoService:sayHello(java.lang.String,int)";
    private static final String interfaceResource = "com.abin.lee.hbase.dubbo.facade.DemoService";
    private static ClassPathXmlApplicationContext context;


    public static void main(String[] args) throws Exception {
        context = new ClassPathXmlApplicationContext(
                new String[]{"spring-dubbo-consumer-filter.xml"});
        context.start();

//        testLimitFlowQps();

//        testFlowRule();

        testDegradeRule();

    }


    public static void testConsumerFilter() throws Exception {
        BusinessService businessService = (BusinessService) context.getBean("businessService");
        String result = businessService.doFlow("Test dubbo consumer filter", 2);
        System.out.println("result=" + result);
        ClusterNode node = ClusterBuilderSlot.getClusterNode(resource);
        Assert.assertNotNull(node);
    }


    public static void testFlowRule() throws Exception {
        initFlowRule();
//        initDegradeRule();


        BusinessService businessService = (BusinessService) context.getBean("businessService");
        for (int i = 0; i < 100_000; i++) {
            try {
                businessService.doFlow("Test dubbo consumer filter1 ", i);
            } catch (Exception e) {
                log.error("i=" + i + " ,e=" + e);
            }
        }
    }


    public static void testDegradeRule() throws Exception {
//        initFlowRule();
        initDegradeRule();


        BusinessService businessService = (BusinessService) context.getBean("businessService");
        for (int i = 0; i < 100_000; i++) {
            try {
                businessService.doDegrade("Test dubbo consumer filter1 ", i);
            } catch (Exception e) {
                log.error("i=" + i + " ,e=" + e);
            }
        }
    }


    public static void testConsumerBlock2() throws Exception {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(interfaceResource);
        flowRule.setCount(5);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Arrays.asList(flowRule));

        BusinessService businessService = (BusinessService) context.getBean("businessService");
        for (int i = 0; i < 100_000; i++) {
            businessService.doFlow("Test dubbo consumer filter", 2);
        }
    }


    public static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(resource);
        flowRule.setCount(5);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Arrays.asList(flowRule));
    }

    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(resource);
        // set limit exception count to 4
        rule.setCount(4);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        /**
         * When degrading by {@link RuleConstant#DEGRADE_GRADE_EXCEPTION_COUNT}, time window
         * less than 60 seconds will not work as expected. Because the exception count is
         * summed by minute, when a short time window elapsed, the degradation condition
         * may still be satisfied.
         */
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


}