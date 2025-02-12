package com.register.register_login.service;

import com.register.register_login.model.userModel;
import com.register.register_login.repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class userService {
    @Autowired
    private userRepo repo;

    public userModel registerUser(userModel user){
        return repo.save(user);
    }
    public String validateUser(userModel user){
        if (user.getuserName().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getName().isEmpty()) {

            return "All fields are required";
        }
        return null;
    }
}
