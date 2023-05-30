package edu.td.zy.tik_common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/4/10 16:43
 */
public class DateUtil {

    public static long getTimestampFromDateString(String dateString) {
        // 解析年份和月份
        int year = Integer.parseInt(dateString.substring(0, 2)) + 2000;
        int month = Integer.parseInt(dateString.substring(2));

        // 创建 LocalDate 对象，设置年份和月份
        LocalDate localDate = LocalDate.of(year, month, 1);

        // 通过 LocalDate 对象获取 LocalDateTime 对象，设置日期和时间
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // 将 LocalDateTime 对象转换为时间戳
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() / 1000;
    }
}
