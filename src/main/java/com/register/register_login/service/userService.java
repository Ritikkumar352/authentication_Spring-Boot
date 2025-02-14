package com.register.register_login.service;

import com.register.register_login.model.userModel;
import com.register.register_login.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class userService {
    @Autowired
    private userRepo repo;


    // Saving Data into db

    public String validateUser(userModel user) {
        if (user.getuserName() == null || user.getuserName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty()) {
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

        if (repo.findByEmail(user.getEmail()).isPresent()) {
            response.put("message", "Email already Exists");
            return ResponseEntity.badRequest().body(response);
        } else if (repo.findByuserName(user.getuserName()).isPresent()) {
            response.put("message", "userName already exists");
            return ResponseEntity.badRequest().body(response);
        }

        //sent current password .getPass and then .setPass (hashed pass)
        user.setPassword(PasswordHasher.hashPassword(user.getPassword()));

        try {
            repo.save(user);
            response.put("message", "User Registered Successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Registration Failed");
            return ResponseEntity.status(500).body(response);
        }
    }

    // ## Login Logic

    public ResponseEntity<Map<String, String>> loginUser(userModel user) {
        Map<String, String> response = new HashMap<>();
        // Make sure these two return same user;
//        Optional<userModel> loginUser = repo.findByuserName(user.getuserName());
//        Optional<userModel> loginEmail = repo.findByEmail(user.getEmail());
        Optional<userModel> foundUser = repo.findByuserNameOrEmail(user.getuserName(), user.getEmail());

        // Password Matching logic


        if (foundUser.isPresent()) {
            response.put("message", "Login Successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }


    // TEST DB SPEED-- ***********************

    //--*******************************


    // Extracting Data from db
    // For login.
    // check is it required
    public userModel getUserByEmail(String email) {
        Optional<userModel> user = repo.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User with this 'Email' Not Found");
        }
    }

    public userModel getUserByUserName(String userName) {
        Optional<userModel> user = repo.findByuserName(userName);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User with this 'userName' Not Found");
        }
    }

}
