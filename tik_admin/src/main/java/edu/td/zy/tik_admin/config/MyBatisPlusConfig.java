package edu.td.zy.tik_admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import edu.td.zy.tik_admin.utils.PhoneTableNameHandler;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/6 9:34
 */
@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = {"edu.td.zy.tik_admin.mapper"})
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

    /**
     * 动态表名
     * */
    @Bean
    public MybatisPlusInterceptor dynamicTableName() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor dynamicTabNameInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTabNameInterceptor.setTableNameHandler(new PhoneTableNameHandler("user"));
        interceptor.addInnerInterceptor(dynamicTabNameInterceptor);
        return interceptor;
    }

    /**
     * 乐观锁
     * */
    @Bean
    public MybatisPlusInterceptor optimisticLocker() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
