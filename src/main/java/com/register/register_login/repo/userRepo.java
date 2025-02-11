package com.register.register_login.repo;

import com.register.register_login.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<userModel,Integer> {
//    userModel findByUsername(String userName);
    // No use at this time
}
