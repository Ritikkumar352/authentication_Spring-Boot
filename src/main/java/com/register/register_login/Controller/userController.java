package com.register.register_login.Controller;

import com.register.register_login.model.userModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.register.register_login.service.userService;

@RestController
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/")

    public String home() {
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody userModel user) {
        String validate = userService.validateUser(user);
        if (validate != null) {
            return ResponseEntity.badRequest().body(validate);
        }
//        System.out.println("UserName:- "+ user.getuserName())-- to access current fields;
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("{" +
                    "\"message\":" +
                    " \"User Registered Successfully\"" +
                    "}"
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Registration Failed");
        }
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
