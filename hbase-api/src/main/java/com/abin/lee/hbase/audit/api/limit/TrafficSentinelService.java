package com.abin.lee.hbase.audit.api.limit;

import com.abin.lee.hbase.audit.api.constant.SentinelKey;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2019/2/2.
 */
@Component
public class TrafficSentinelService {

    @PostConstruct
    public void init() {
        //qps
        initFlowQpsRule();
        //degrade
        initDegradeRule();
    }


    public void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(SentinelKey.flowDegradeKey);
        // set limit qps to 20
        rule1.setCount(10);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    public void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(SentinelKey.flowDegradeKey);
        // set threshold rt, 10 ms
        rule.setCount(10);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(20);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    public void initFlowQpsRule(Integer count) {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(SentinelKey.flowDegradeKey);
        // set limit qps to 20
        rule1.setCount(count);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    public void initDegradeRule(Integer count, Integer timeWindow) {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(SentinelKey.flowDegradeKey);
        // set threshold rt, 10 ms
        rule.setCount(count);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(timeWindow);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }




}
