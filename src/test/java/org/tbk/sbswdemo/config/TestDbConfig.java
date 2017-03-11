package org.tbk.sbswdemo.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.util.Properties;

@Configuration
public class TestDbConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:~test.db");
        return dataSourceBuilder.build();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws URISyntaxException {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }


    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        /*
        http://stackoverflow.com/questions/438146/hibernate-hbm2ddl-auto-possible-values-and-what-they-do
        Stackoverflow description of what each hibernate.hbm2ddl.auto param does
         */
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        //hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", org.tbk.sbswdemo.sqlite.SQLiteDialect.class.getName());
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");

        return hibernateProperties;
    }
}
