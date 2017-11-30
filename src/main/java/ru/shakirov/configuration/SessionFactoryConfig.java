package ru.shakirov.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ru.shakirov")
@EnableTransactionManagement
public class SessionFactoryConfig {

    @Bean
    public DataSource dataSource() {
        String dbUrl = "jdbc:sqlite:" + SessionFactoryConfig.class.getResource("/").getPath() + "db.sqlite";
        DriverManagerDataSource ds = new DriverManagerDataSource(dbUrl);
        ds.setDriverClassName("org.sqlite.JDBC");
        return ds;
    }

    @Bean
    @DependsOn(value = "dataSource")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");

        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        sessionFactoryBean.setPackagesToScan("ru.shakirov.entity");
        return sessionFactoryBean;
    }

    @Bean
    @DependsOn(value = "sessionFactory")
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory().getObject());
        return manager;
    }
}
