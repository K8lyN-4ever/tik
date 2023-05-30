package edu.td.zy.tik_admin.utils;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.util.Arrays;
import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/6 9:27
 */
public class PhoneTableNameHandler implements TableNameHandler {

    private final List<String> tableNames;
    private static final ThreadLocal<String> PHONE_DATA = new ThreadLocal<>();

    public PhoneTableNameHandler(String ...tableNames) {
        this.tableNames = Arrays.asList(tableNames);
    }

    public static void setData(String phone) {
        setData(phone, 2);
    }

    public static void setData(String phone, int index) {
        PHONE_DATA.set(phone.substring(0, index));
    }

    public static void removeData() {
        PHONE_DATA.remove();
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if(this.tableNames.contains(tableName)) {
            return tableName + "_" + PHONE_DATA.get();
        }else {
            return tableName;
        }
    }
}
