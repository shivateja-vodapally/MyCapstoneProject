package com.example.UserService.Security.Service;

import com.example.UserService.Models.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Security.Models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser=userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Not Found");
        }

        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
