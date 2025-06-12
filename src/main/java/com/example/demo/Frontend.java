package com.example.demo;

import org.springframework.stereotype.Controller;

@Controller
public class Frontend {
    public String index() {
        return "index"; // This should map to a Thymeleaf template named index.html
    }
}
