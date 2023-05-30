package edu.td.zy.tik_common.utils.media;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/2 10:56
 */
public class Clip extends MediaUtil {

    public static long[] parseRange(String rangeHeader, long totalLength) {
        // 解析range请求头，获取请求范围
        // 请求头示例： bytes=0-1023
        String[] rangeValues = rangeHeader.substring(6).split("-");
        long start = Long.parseLong(rangeValues[0]);
        long end = rangeValues.length > 1 ? Long.parseLong(rangeValues[1]) : totalLength - 1;
        return new long[]{start, end};
    }
}
