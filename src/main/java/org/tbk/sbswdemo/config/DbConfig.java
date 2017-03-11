package org.tbk.sbswdemo.config;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public DbConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public DataSource dataSource() {
        final String dbName = Strings.isNullOrEmpty(applicationProperties.getName()) ?
                "database" : applicationProperties.getName();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(org.sqlite.JDBC.class.getName());
        dataSourceBuilder.url("jdbc:sqlite:" + dbName + ".db");
        return dataSourceBuilder.build();
    }
}
