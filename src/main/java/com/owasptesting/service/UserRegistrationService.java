package com.owasptesting.service;

import com.owasptesting.model.User;
import com.owasptesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationService {
@Autowired
    private UserRepository userRepository;

    public User checkExistingUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User userRegistered = new User(user.getEmail(), user.getMobileNumber(),user.getUserName(),
                encoder.encode(user.getPassWord()));
        userRepository.save(userRegistered);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
