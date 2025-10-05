package com.example.UserService.Service;

import com.example.UserService.Models.User;
import com.example.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserDetails(Long id) {
        return userRepository.findById(id).get();
    }
}
