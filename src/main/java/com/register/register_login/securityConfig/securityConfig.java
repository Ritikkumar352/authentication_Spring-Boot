package com.register.register_login.securityConfig;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class securityConfig {
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "home";
    }

    @PostMapping("/login")
    public void login(){

    }
    @PostMapping("/register")
    public void register(){

    }

    @GetMapping("/about")
    @ResponseBody
    public String about(){
        return "about";
    }

}
