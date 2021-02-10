package com.makeen.assignment.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("welcome")
public class HomePageAPI {

    @GetMapping("user")
    public String homeUser() {
        return "Welcome User";
    }

    @GetMapping("admin")
    public String homeAdmin() {
        return "Welcome Admin";
    }
}