package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class WebController {

    @GetMapping("/welcome")
    public String welcome(@RequestParam(name = "username", required = false) String username, Model model) {

        StringBuilder sb = new StringBuilder();

        if (username != null && !username.isEmpty()) {
            model.addAttribute("username", username);

           

            sb.append("<!DOCTYPE html>\r\n" + //
                                "<html xmlns:th=\"http://www.thymeleaf.org\">\r\n" + //
                                "<head>\r\n" + //
                                "    <title>Welcome Page</title>\r\n" + //
                                "<style>\r\n" + //
                                "    body {\r\n" + //
                                "        font-family: 'Poppins', sans-serif;\r\n" + //
                                "        font-weight: 300;\r\n" + //
                                "        line-height: 1.7;\r\n" + //
                                "        color: #ffeba7;\r\n" + //
                                "        background-color: #1f2029;\r\n" + //
                                "        align-items: center;\r\n" + //
                                "        height: 100%;\r\n" + //
                                "        margin: 0;\r\n" + //
                                "        display: flex;\r\n" + //
                                "        justify-content: center;\r\n" + //
                                "    }\r\n" + //
                                "</style>\r\n" + //
                                "</head>\r\n" + //"
                                "<body>\r\n" + //
                                "    <h1>Welcome, "+ username +"</span>!</h1>\r\n" + //
                                "</body>\r\n" + //
                                "</html>\r\n" + //
                                "");

            System.out.println("in /welcome" +username);
        }
        return sb.toString();


        
    }
}
