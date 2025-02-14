package com.register.register_login.service;

import com.register.register_login.model.userModel;
import com.register.register_login.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//  ####################################################  //
// use 1. //# for main operation
// use 2. //* for operations in main operation
// use 3. //- for smaller opration useing prev defind methods


@Service
public class userService {
    @Autowired
    private userRepo repo;

    //* input validation before query

    // - login input validation
    public String validateLoginUser(userModel user) {
        if (user.getuserName() == null || user.getPassword() == null) {
            return "1";
        } else {
            return "username or password cannot be empty";
        }
    }

    // - Registration input validation
    public String validateRegUser(userModel user) {
        if (user.getuserName() == null || user.getuserName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getName() == null || user.getName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty()) {
            return "All fields are required";
        }
        return null;
    }

    //## Saving Data into db

        // * Register Uuser
    public ResponseEntity<Map<String, String>> registerUser(userModel user) {
        Map<String, String> response = new HashMap<>();
        String validate = validateRegUser(user);
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

        // * Login user Logic
    public ResponseEntity<Map<String, String>> loginUser(userModel user) {
        Map<String, String> response = new HashMap<>();

        // -validate login input fileds
        String validate = validateLoginUser(user);
        System.out.println(validate+"-- login vallll");


        userModel foundUser = repo.findByuserNameOrEmail(user.getuserName(), user.getEmail());

        if (foundUser == null) {
            response.put("message", "User Not Found");
            return ResponseEntity.badRequest().body(response);
        } else if (foundUser.getuserName().equals(user.getuserName())) {
            String hashPassCheck = PasswordHasher.hashPassword(user.getPassword());
            if (foundUser.getPassword().equals(hashPassCheck)) {
                response.put("message", "Login Successful");
            } else {
                response.put("message", "Wrong Password");
            }
        }
        return ResponseEntity.ok(response);
    }


    // #Session Management, secure access to other routes







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
