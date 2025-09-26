package com.example.UserService.Controller;

import com.example.UserService.DTO.LoginRequestDTO;
import com.example.UserService.DTO.SignUpRequestDTO;
import com.example.UserService.DTO.UserDTO;
import com.example.UserService.Models.User;
import com.example.UserService.Service.AuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    //Signup request
    @PostMapping("auth/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO)
    {
        User user=authService.signUp(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());
        UserDTO userDTO=getUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        try
        {
            Pair<User, MultiValueMap<String,String>> bodyWithHeaders =authService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());
            UserDTO userDTO=getUserDTO(bodyWithHeaders.a);
            return new ResponseEntity<>(userDTO, bodyWithHeaders.b,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    private UserDTO getUserDTO(User user)
    {
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

}
