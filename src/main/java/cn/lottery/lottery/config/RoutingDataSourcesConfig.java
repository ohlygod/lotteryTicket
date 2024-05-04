package cn.lottery.lottery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * 把多个数据源，装配到一个 RoutingDataSource 里
 * @author :Java课代表
 */
@Configuration
public class RoutingDataSourcesConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public DynamicDataSource routingDataSource(DataSource dataSource0, DataSource dataSource1) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("first", dataSource0);
        targetDataSources.put("second", dataSource1);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(dataSource0);

        return dynamicDataSource;
    }

}
