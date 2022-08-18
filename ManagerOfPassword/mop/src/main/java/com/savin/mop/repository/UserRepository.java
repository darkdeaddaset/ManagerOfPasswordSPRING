package com.savin.mop.repository;

import com.savin.mop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(long id);
    Optional<User> getUserByName(String name);
    Optional<User> getUserByNameAndPassword(String name, String password);
}
