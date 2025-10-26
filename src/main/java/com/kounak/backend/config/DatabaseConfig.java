package com.kounak.backend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("production")
public class DatabaseConfig {

    @Value("${DATABASE_URL:jdbc:mysql://localhost:3306/fashionDB?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        try {
            // If it's a mysql:// URL from Railway, convert it to jdbc:mysql://
            if (databaseUrl.startsWith("mysql://")) {
                URI uri = new URI(databaseUrl);
                String host = uri.getHost();
                int port = uri.getPort();
                String path = uri.getPath();
                String userInfo = uri.getUserInfo();

                String[] credentials = userInfo.split(":");
                String username = credentials[0];
                String password = credentials[1];

                String jdbcUrl = String.format("jdbc:mysql://%s:%d%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                        host, port, path);

                dataSource.setJdbcUrl(jdbcUrl);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

                // Railway specific connection pool settings
                dataSource.setMaximumPoolSize(10);
                dataSource.setMinimumIdle(2);
                dataSource.setIdleTimeout(300000);
                dataSource.setMaxLifetime(600000);
                dataSource.setConnectionTimeout(30000);
                dataSource.setLeakDetectionThreshold(60000);

                System.out.println("Configured Railway database connection: " + host + ":" + port + path);
            } else {
                // Fallback to standard JDBC URL
                dataSource.setJdbcUrl(databaseUrl);
                dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            }

            return dataSource;

        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid DATABASE_URL format: " + e.getMessage(), e);
        }
    }
}