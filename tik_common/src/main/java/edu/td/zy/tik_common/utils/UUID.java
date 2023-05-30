package edu.td.zy.tik_common.utils;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/2 10:49
 */
public class UUID {

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
