package com.mjc.school.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    @Bean
    public DataSource dataSource(@Value("${db.user}") String username,
                                 @Value("${DB_PASS}") String password,
                                 @Value("${DB_HOST}") String url) {
        DataSourceProperties properties = new DataSourceProperties();
        //TODO remove
        if (password.equals("256925"))
            password="2569";
        //TODO
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setUrl(url);
        return properties.initializeDataSourceBuilder().build();
    }
}
