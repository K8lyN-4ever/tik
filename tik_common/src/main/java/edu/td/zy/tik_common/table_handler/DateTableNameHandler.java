package edu.td.zy.tik_common.table_handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/6 9:27
 */
public class DateTableNameHandler implements TableNameHandler {

    private final List<String> tableNames;
    private static final ThreadLocal<String> DATE_DATA = new ThreadLocal<>();

    public DateTableNameHandler(String ...tableNames) {
        this.tableNames = Arrays.asList(tableNames);
    }

    public static void setData(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        Date date = new Date(timestamp * 1000);
        DATE_DATA.set(sdf.format(date));
    }

    public static void removeData() {
        DATE_DATA.remove();
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if(this.tableNames.contains(tableName)) {
            return tableName + "_" + DATE_DATA.get();
        }else {
            return tableName;
        }
    }
}
