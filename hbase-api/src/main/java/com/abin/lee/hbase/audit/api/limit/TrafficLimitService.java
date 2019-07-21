package com.abin.lee.hbase.audit.api.limit;

import com.google.common.primitives.Doubles;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by lee on 2019/1/28.
 */
@Component
public class TrafficLimitService {
    private RateLimiter rateLimiter = null;

    @PostConstruct
    public void init() {
        rateLimiter = RateLimiter.create(10.0);
    }

    public boolean tryAcquire() {
        boolean flag = rateLimiter.tryAcquire();
        return flag;
    }

    public boolean tryAcquire(int permits) {
        boolean flag = rateLimiter.tryAcquire(permits, 1, TimeUnit.SECONDS);
        return flag;
    }

    public void setRate(String param) {
        rateLimiter.setRate(Doubles.tryParse(param));
    }



    public void setLimit(String param) {
        rateLimiter = RateLimiter.create(Doubles.tryParse(param));
    }



    public Double getLimit() {
        Double limit = rateLimiter.getRate();
        return limit;
    }


    public static void main(String[] args) {
        TrafficLimitService trafficLimit = new TrafficLimitService();
        System.out.println(trafficLimit.getLimit());
        trafficLimit.setLimit(10 + "");
        System.out.println(trafficLimit.getLimit());
    }




}
