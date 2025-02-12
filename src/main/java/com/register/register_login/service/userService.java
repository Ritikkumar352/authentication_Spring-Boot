package com.register.register_login.service;

import com.register.register_login.model.userModel;
import com.register.register_login.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class userService {
    @Autowired
    private userRepo repo;

    //    public userModel registerUser(userModel user){
    //        return repo.save(user);
    //    }
    public String validateUser(userModel user) {
        if (user.getuserName().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getName().isEmpty() ||
                user.getLastName().isEmpty()) {
            return "All fields are required";
        }
        return null;
    }

    public ResponseEntity<Map<String, String>> registerUser(userModel user) {
        Map<String, String> response = new HashMap<>();
        String validate = validateUser(user);
        if (validate != null) {
            response.put("message", validate);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            repo.save(user);
            response.put("message", "User Registered Successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Registration Failed");
            return ResponseEntity.status(500).body(response);
        }
    }
}
