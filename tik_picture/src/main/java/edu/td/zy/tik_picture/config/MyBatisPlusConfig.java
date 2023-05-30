package edu.td.zy.tik_picture.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import edu.td.zy.tik_common.table_handler.DateTableNameHandler;
import edu.td.zy.tik_common.table_handler.PhoneTableNameHandler;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/27 9:30
 */
@Configuration
@RequiredArgsConstructor
@MapperScan("edu.td.zy.tik_picture.mapper")
public class MyBatisPlusConfig {

    private final DataSource dataSource;

    /**
     * SqlSessionFactory
     * */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setPlugins(dynamicTableName(),
                                optimisticLocker());
        return mybatisPlus;
    }

    @Bean
    public MybatisPlusInterceptor dynamicTableName() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new PhoneTableNameHandler("picture"));
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new DateTableNameHandler("picture_detail"));
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    @Bean
    public MybatisPlusInterceptor optimisticLocker() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
