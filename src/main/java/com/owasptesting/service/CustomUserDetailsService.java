package com.owasptesting.service;

import com.owasptesting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRegistrationService userServiceImpl;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.checkExistingUser(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomUserDetails(user);
    }

}
