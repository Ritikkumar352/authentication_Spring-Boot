package com.register.register_login.model;

import jakarta.persistence.*;

@Entity
@Table(name="authUser")
public class userModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "userName")
    private String userName;
    private String password;
    private String email;
    private String name;
    @Column(name = "lastName")  // why not worked ?
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "userModel{" + "id=" + id + ", userName='" + userName + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}