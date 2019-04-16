package com.abin.lee.hbase.audit.api.constant;

/**
 * Created by lee on 2019/2/11.
 */
public class SwitchKey {

    public static String openKey = "";
    public static Integer limitKey = 0;

    static {
        openKey = "ON";
        limitKey = 5;
    }

    public static String putOpenKey(String param) {
        System.out.println("openKey=" + openKey + ",param=" + param);
        openKey = param;
        System.out.println("openKey=" + openKey + ",param=" + param);
        return openKey ;
    }

    public static String putLimitKey(Integer param) {
        System.out.println("openKey=" + openKey + ",param=" + param);
        limitKey = param;
        System.out.println("openKey=" + openKey + ",param=" + param);
        return limitKey+"" ;
    }


}
