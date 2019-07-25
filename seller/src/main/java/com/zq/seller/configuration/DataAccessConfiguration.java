package com.zq.seller.configuration;

import com.zq.entity.Order;
import com.zq.seller.repositories.OrderRepository;
import com.zq.seller.repositories.VerifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库相关操作配置
 */
@Configuration
public class DataAccessConfiguration {
    @Autowired
    private JpaProperties properties;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSource backupDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary") //这里主要名称唯一就可以了
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean backupEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("backupDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup") //这里主要名称唯一就可以了
                .build();
    }

    protected Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, Object> vendorProperties = new LinkedHashMap();
        vendorProperties.putAll(properties.getHibernateProperties(dataSource));
        return vendorProperties;
    }

    @Bean
    @Primary
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(primaryEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager backupTransactionManager(@Qualifier("backupEntityManagerFactory") LocalContainerEntityManagerFactoryBean backupEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(backupEntityManagerFactory.getObject());
        return transactionManager;
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "primaryEntityManagerFactory",transactionManagerRef = "primaryTransactionManager")
//    @Primary
    public class PrimaryConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = VerifyRepository.class,
            entityManagerFactoryRef = "backupEntityManagerFactory",transactionManagerRef = "backupTransactionManager")
    public class BackupConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "backupEntityManagerFactory",transactionManagerRef = "backupTransactionManager")
    @RepositoryBeanNamePrefix("read")
    public class ReadConfiguration {
    }
}