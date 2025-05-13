package com.example.demo.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionUtil {

    private HikariDataSource ds;

    @Value("${spring.datasource.driver-class-name}")
    private String dbdriver;
    @Value("${spring.datasource.url}")
    private String dburl;
    @Value("${spring.datasource.username}")
    private String dbuser;
    @Value("${spring.datasource.password}")
    private String dbpw;

    @PostConstruct
    public void init() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dbdriver);
        config.setJdbcUrl(dburl);
        config.setUsername(dbuser);
        config.setPassword(dbpw);

        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
