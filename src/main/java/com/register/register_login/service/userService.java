package com.register.register_login.service;

import com.register.register_login.dto.userDTO;
import com.register.register_login.model.userModel;
import com.register.register_login.repo.userRepo;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private HttpSession session;

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
        System.out.println(validate + "-- login vallll");

        userModel foundUser = repo.findByuserNameOrEmail(user.getuserName(), user.getEmail());
        if (foundUser == null) {
            response.put("message", "User Not Found");
            return ResponseEntity.badRequest().body(response);
        } else if (foundUser.getuserName().equals(user.getuserName())) {
            String hashPassCheck = PasswordHasher.hashPassword(user.getPassword());
            if (foundUser.getPassword().equals(hashPassCheck)) {
//
                // Store user data in session for secure access
                session.setAttribute("userId", foundUser.getId());
                session.setAttribute("name", foundUser.getName());
                session.setAttribute("userName", foundUser.getuserName());
                session.setAttribute("email", foundUser.getEmail());

// Fetch again to verify session attributes... this tells java to GIVE IT NOW !!
                Object sessionUserId = session.getAttribute("userId");
                Object sessionUserName = session.getAttribute("userName");
                Object sessionEmail = session.getAttribute("email");

// Log session values
                System.out.println("Session ID: " + session.getId());
                System.out.println("Session User ID: " + sessionUserId);
                System.out.println("Session UserName: " + sessionUserName);
                System.out.println("Session Email: " + sessionEmail);

                response.put("message", "Login Successful");
                return ResponseEntity.ok(response);


//                // logsss
//                System.out.println("Session ID before setting attributes: " + session.getId());
//                // * Store user data in session for secure access
//                session.setAttribute("userId", (int)foundUser.getId());
//              System.out.println("Session ID after setting attributes: " + session.getAttribute("userId"));
//                session.setAttribute("name", foundUser.getName());
//                session.setAttribute("userName", foundUser.getuserName());
//                session.setAttribute("email", foundUser.getEmail());
//                // logs System.out.println("Session ID after setting attributes: " + session.getId());
//                System.out.println("Session User ID: " + session.getAttribute("userId"));
//                System.out.println("Session UserName: " + session.getAttribute("userName"));
//                System.out.println("Session Email: " + session.getAttribute("email"));
//                response.put("message", "Login Successful");
//                 this line was missing .. it was not returning data
//                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Wrong Password");
            }
        }
        return ResponseEntity.ok(response);
    }


    // #Session Management, secure access to other routes

    public ResponseEntity<Map<String, String>> isUserLoggedIn() {
        Map<String, String> response = new HashMap<>();
        if (session.getAttribute("userId") == null) {
            response.put("message", "User Not Logged In-- isUserLoggedIn class {B]");
            return ResponseEntity.badRequest().body(response);
        }// expanded if-else for better understandin

        response.put("message", "User Logged In-- isUserLoggedIn class {B]");
        return ResponseEntity.ok(response);
    }

    public Map<String, Object> getSessionData() {
        Map<String, Object> sessionData = new HashMap<>();
        if (session.getAttribute("userId") != null) {
            System.out.println("UserId is not null");
            sessionData.put("userId", session.getAttribute("userId"));
            sessionData.put("userName", session.getAttribute("userName"));
            sessionData.put("name", session.getAttribute("name"));
            sessionData.put("email", session.getAttribute("email"));
            return sessionData;
        } else {
            System.out.println("UserId is NULL");
            sessionData.put("message", "User Not Logged In-- getSessionData method {B]");
        }
        return sessionData;
    }

    //## UPDATE DATA in DB

    //** Profile Update method
    public ResponseEntity<Map<String, String>> update(userDTO updatedUser) {
        Map<String, String> response = new HashMap<>();
        Optional<userModel> updateUser = repo.findUserById(updatedUser.getId());
        if (updateUser.isPresent()) {
            userModel foundUser = updateUser.get();
            foundUser.setName(updatedUser.getName());
            foundUser.setLastName(updatedUser.getLastName());
            System.out.println("Updating User: " + foundUser);

            repo.save(foundUser);
            repo.flush(); //force --
            response.put("message", "user Details updated");
            return ResponseEntity.ok(response);
        }

        response.put("message", "User Not Found");
        return ResponseEntity.badRequest().body(response);
    }


    public ResponseEntity<Map<String, String>> logoutUser() {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }


    //## Delete from DB
    public ResponseEntity<Map<String, String>> deleteUser(Integer userId) {
        Map<String, String> response = new HashMap<>();
        Optional<userModel> user=repo.findUserById(userId);
        if (user.isPresent()) {
            repo.delete(user.get());

            response.put("message", "User Deleted Successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "User Not Found");
        return ResponseEntity.badRequest().body(response);
    }

    // ** get current loggedIn user details
    public int getLoggedInUserId() {
        return (int) session.getAttribute("userId");
    }

    public String getLoggedInUserName() {
        return (String) session.getAttribute("userName");
    }

    public String getLoggedInName() {
        return (String) session.getAttribute("name");
    }

    public String getLoggedInEmail() {
        return (String) session.getAttribute("email");
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
