package com.example.dynamicDruid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
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
@MapperScan(basePackages = "com.example.dynamicDruid.mapper.td", sqlSessionFactoryRef = "tdSqlSessionFactory")
public class TdDataSourceConfig {

    // 将这个对象放入Spring容器中
    @Bean(name = "tdDataSource")
    // @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.td") // prefix表示参数的前缀，读取application.properties中的配置参数映射成为一个对象
    public DataSource getTdDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "tdSqlSessionFactory")
    // @Primary
    public SqlSessionFactory tdSqlSessionFactory(@Qualifier("tdDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/td/*.xml"));
        return bean.getObject();
    }

    @Bean("tdSqlSessionTemplate")
    // @Primary
    public SqlSessionTemplate tdSqlSessionTemplate(@Qualifier("tdSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

}