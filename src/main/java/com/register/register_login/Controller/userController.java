package com.register.register_login.Controller;

import com.register.register_login.dto.userDTO;
import com.register.register_login.model.userModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.register.register_login.service.userService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class userController {
    @Autowired
    private userService userService;

    @Autowired
    HttpSession session;

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

//    @GetMapping("/session-check")
//    public ResponseEntity<Map<String, String>> sessionCheck() {
//        return userService.isUserLoggedIn();
//    }

    @PatchMapping("/updateProfile")
    public ResponseEntity<Map<String,String>> update(@RequestBody userDTO user) {
        return userService.update(user);
    }

    @GetMapping("/session-data")
    public Map<String, Object> sessionData() {
        // working in POSTMAN but not on react
        System.out.println("Calling session data....");
        System.out.println("Session data----- username");
        System.out.println(session.getAttribute("userName"));
        return userService.getSessionData();

    }

    // Logout is working in PostMan ... fix React code
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return userService.logoutUser();
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody int id) {
        return userService.deleteUser(id);
    }

}
