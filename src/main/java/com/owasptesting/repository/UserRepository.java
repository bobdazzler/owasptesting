package com.owasptesting.repository;

import com.owasptesting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("SELECT user FROM User user WHERE user.id = ?1")
    User getUserById(int id);
}
