package com.abin.lee.hbase.audit.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by abin on 2018/7/4.
 * https://blog.csdn.net/soldier_123/article/details/78917227
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@ImportResource("classpath:spring/spring-hbase.xml")      //导入xml配置
public class HbaseAuditApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
//        ApplicationContext ctx = SpringApplication.run(NearbyApplication.class, args);
//        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
//        for (String profile : activeProfiles) {
//            log.warn("---------Spring Boot 使用profile为:{}" , profile);
//        }

        SpringApplication.run(HbaseAuditApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    bootdo启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                " ______                    _   ______            \n" +
                "|_   _ \\                  / |_|_   _ `.          \n" +
                "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
                "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
                " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
                "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(HbaseAuditApplication.class);
    }



}
