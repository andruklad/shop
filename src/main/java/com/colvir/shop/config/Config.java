package com.colvir.shop.config;

import com.colvir.shop.repository.CatalogRepository;
import com.colvir.shop.repository.CategoryRepository;
import com.colvir.shop.repository.ProductRepository;
import com.colvir.shop.repository.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class Config {

    private final String repositoryMode;
    private final String driver;
    private final String url;
    private final String username;
    private final String password;
    private final String hibernateDialect;
    private final String hibernateShowSql;

    public Config(
            @Value("${repository.mode}") String repositoryMode,
            @Value("${database.driver}") String driver,
            @Value("${database.url}") String url,
            @Value("${database.username}") String username,
            @Value("${database.password}") String password,
            @Value("${hibernate.dialect}") String hibernateDialect,
            @Value("${hibernate.show.sql}") String hibernateShowSql) {

        this.repositoryMode = repositoryMode;
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.hibernateDialect = hibernateDialect;
        this.hibernateShowSql = hibernateShowSql;
    }

    @Bean
    public DataSource getDataSource() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driver);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public CatalogRepository getCatalogRepository () {

        return switch (repositoryMode) {
            case "memory" -> new CatalogRepositoryMemoryImpl();
            case "postgres" -> new CatalogRepositoryPostgresImpl(getJdbcTemplate());
            default ->
                    throw new RuntimeException(String.format("Режим репозитория %s не поддерживается.", repositoryMode));
        };
    }

    @Bean
    public CategoryRepository getCategoryRepository () {

        return switch (repositoryMode) {
            case "memory" -> new CategoryRepositoryMemoryImpl();
            case "postgres" -> new CategoryRepositoryPostgresImpl(getLocalSessionFactoryBean().getObject());
            default ->
                    throw new RuntimeException(String.format("Режим репозитория %s не поддерживается.", repositoryMode));
        };
    }

    @Bean
    public ProductRepository getProductRepository () {

        return switch (repositoryMode) {
            case "memory" -> new ProductRepositoryMemoryImpl();
            // TODO. Расскомментировать после реализации
//            case "postgres" -> new ProductRepositoryPostgresImpl(getLocalSessionFactoryBean().getObject());
            default ->
                    throw new RuntimeException(String.format("Режим репозитория %s не поддерживается.", repositoryMode));
        };
    }

    private Properties getHibernateProperties() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", hibernateShowSql);
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean getLocalSessionFactoryBean() {

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
        localSessionFactoryBean.setPackagesToScan("com.colvir.shop.model");
        return localSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManager() {

        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(getLocalSessionFactoryBean().getObject());
        return hibernateTransactionManager;
    }
}
