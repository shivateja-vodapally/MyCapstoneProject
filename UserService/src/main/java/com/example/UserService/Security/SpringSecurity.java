package com.example.UserService.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SpringSecurity {

     //Here we have disabled the bean because in springconfig we have same securityFilterchain bean
     //Both cant be there at a time in spring version 3.5.6. But it worked for instructor with 3.2.3.
     //So do not go to previous version as it may lead to other dependency falures like lombok
     //Due to version incompatibility
     //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests(authorize-> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey getSecretKey()
    {
        MacAlgorithm algorithm= Jwts.SIG.HS256;
        SecretKey secret=algorithm.key().build();
        return secret;
    }
}
