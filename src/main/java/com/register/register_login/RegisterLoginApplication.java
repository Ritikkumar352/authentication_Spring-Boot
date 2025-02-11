package com.register.register_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.register.register_login.model")
@EnableJpaRepositories("com.register.register_login.repo")

public class RegisterLoginApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(RegisterLoginApplication.class, args);
    }

}
