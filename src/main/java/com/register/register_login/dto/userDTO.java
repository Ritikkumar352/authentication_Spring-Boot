package com.register.register_login.dto;

import lombok.Setter;

public class userDTO {
    // not updating userName, password and email rn.. need to validate before updating
    private String name;
    @Setter
    private String lastName;
//    private String email;
    private int id;

    public int getId(){
        return id;
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

}
