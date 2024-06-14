package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.demo.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Configuration
@ComponentScan(basePackages = "com.example")
@EnableAutoConfiguration
@EntityScan(basePackages = "com.example")
@SpringBootApplication
public class DemoApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

   

	@Bean
    public CommandLineRunner run() {
		
        return args -> {
            //String username = "mert"; 
            //String sql = "SELECT * FROM users WHERE username = '" + username + "'";
			String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                        "username VARCHAR(255) UNIQUE NOT NULL," +
                        "password_hash VARCHAR(255) NOT NULL," +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
            System.out.println(sql);

            try {
                jdbcTemplate.update(sql);
            } catch (Exception e) {
                System.out.println(e);
            }
        };
    }
}

// "INSERT INTO users (" + username + "," + password + ")" 