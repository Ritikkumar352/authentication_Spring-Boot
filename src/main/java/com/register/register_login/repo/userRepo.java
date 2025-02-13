package com.register.register_login.repo;

import com.register.register_login.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<userModel,Integer> {
    Optional<userModel> findByEmail(String email);

    Optional<userModel> findByuserName(String userName);

//    userModel findByUsername(String userName);

}
