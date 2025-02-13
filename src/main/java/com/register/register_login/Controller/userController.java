package com.register.register_login.Controller;

import com.register.register_login.model.userModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.register.register_login.service.userService;

import java.util.Map;

@RestController
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/")

    public String home() {
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody userModel user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody userModel user) {
        return userService.loginUser(user);

    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
