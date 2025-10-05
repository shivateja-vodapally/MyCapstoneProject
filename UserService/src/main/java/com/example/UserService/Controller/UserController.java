package com.example.UserService.Controller;

import com.example.UserService.DTO.UserDTO;
import com.example.UserService.Models.User;
import com.example.UserService.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO GetUserDetails(@PathVariable Long id) {
        System.out.println(id);
        User user =  userService.getUserDetails(id);
        return getUserDTO(user);
    }

    private UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
