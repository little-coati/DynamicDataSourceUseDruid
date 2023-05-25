package com.example.dynamicDruid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author Karl
 * @since 2023-05-25
 */
@Configuration
@MapperScan(basePackages = "com.example.dynamicDruid.mapper.mysql", sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {

    // 将这个对象放入Spring容器中
    @Bean(name = "mysqlDataSource")
    @Primary // 表示这个数据源是默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.druid.mysql") // prefix表示参数的前缀，读取application.properties中的配置参数映射成为一个对象
    public DataSource getMysqlDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus.configuration")
    public MybatisConfiguration globalConfiguration() {
        return new MybatisConfiguration();
    }

    @Bean(name = "mysqlSqlSessionFactory")
    @Primary // 表示这个数据源是默认数据源
    // @Qualifier表示查找Spring容器中名字为mysqlDataSource的对象
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource datasource, MybatisConfiguration config) throws Exception {
        // 配置多数据源，自定义的sqlSessionFactory不会加载mybatisPlus配置，而单数据源的sqlSessionFactory不是自定义的，默认去加载了mybatisPlus配置，所以要手动加载配置
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/mysql/*.xml"));
        bean.setConfiguration(config);
        return bean.getObject();
    }

    @Bean("mysqlSqlSessionTemplate")
    @Primary // 表示这个数据源是默认数据源
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

}