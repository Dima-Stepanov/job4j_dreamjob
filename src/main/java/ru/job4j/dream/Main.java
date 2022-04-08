package ru.job4j.dream;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 3.2.6. DabaBase в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]
 */
@SpringBootApplication
public class Main {

    private Properties loadDbProperties() {
        Properties config = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Main.class.getClassLoader().getResourceAsStream("db.properties")))) {
            config.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(config.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return config;
    }

    @Bean
    public BasicDataSource loadPool() {
        Properties config = loadDbProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(config.getProperty("jdbc.driver"));
        pool.setUrl(config.getProperty("jdbc.url"));
        pool.setUsername(config.getProperty("jdbc.username"));
        pool.setPassword(config.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMinIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }
}
