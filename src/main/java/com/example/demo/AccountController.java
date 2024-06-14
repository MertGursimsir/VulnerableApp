package com.example.demo;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AccountController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse rsp) {
        /*System.out.println("Login: " + user.getUsername() + " - " + user.getPassword());
        
       */

            String sql = "SELECT * FROM users WHERE username = '" + user.getUsername() + "' AND password_hash = '" + getMd5(user.getPassword()) + "'";
			System.out.println(sql);

            RowMapper<User> rowMapper = (ResultSet rs, int rowNum) -> {
                User user1 = new User();
                user1.setID(rs.getLong("user_id"));
                user1.setUsername(rs.getString("username"));
                /*
                rsp.setHeader("Location", "welcome.html");
                rsp.setStatus(302);
                */
                return user1;
            };
            
            try {
                User user1 = jdbcTemplate.queryForObject(sql, rowMapper);
                System.out.println("Found user: " + user.getUsername() + " with ID: " + user.getID());
                System.out.println(user1.getUsername());
                        
                return ResponseEntity.ok(Map.of(
                    "loginSuccess", true,
                    "userName", user.getUsername(), // Include username in response
                    "isRegister", false
                ));
            } catch (Exception e) {
                System.out.println("User not found\n" + e);
                return ResponseEntity.ok(Map.of(
                    "loginSuccess", false,
                    "isRegister", false
                ));
            }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String sql = "INSERT INTO users (username, password_hash) VALUES (\"" + user.getUsername() + "\" , \"" + getMd5(user.getPassword()) + "\")";
        System.out.println("Register: " + user.getUsername() + " - " + user.getPassword());

        
        try {
            jdbcTemplate.update(sql);
                    
            return ResponseEntity.ok(Map.of(
                "loginSuccess", false,
                "userName", user.getUsername(), // Include username in response
                "isRegister", true
            ));
        } catch (Exception e) {
            System.out.println("Error while registering\n" + e);
            return ResponseEntity.ok(Map.of(
                "loginSuccess", false,
                "isRegister", false
            ));
        }
    }

    public static String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            byte[] messageDigest = md.digest(input.getBytes());
 
            BigInteger no = new BigInteger(1, messageDigest);
 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
