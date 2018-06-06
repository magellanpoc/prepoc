package com.spring.boot.example.ldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String index() {
        return "Welcome to spring boot security example!";
    }
}
