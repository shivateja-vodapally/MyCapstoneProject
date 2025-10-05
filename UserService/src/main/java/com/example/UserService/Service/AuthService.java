package com.example.UserService.Service;

import com.example.UserService.Models.Session;
import com.example.UserService.Models.SessionStatus;
import com.example.UserService.Repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import com.example.UserService.Models.User;
import com.example.UserService.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    //Secret key is moved to configuration file to create as a custom Bean because same
    //key is used to verify the session token to know whether the signature generated from given
    //token in the validate method against session's token(stored one)
    //If we write the secret key generation again then the signature wont match as the secret key
    //changes for every call.
    @Autowired
    private SecretKey secret;

    //As this bean is not present in our project we need to add this bean in our spring security configuration
    //This is to encrypt the password securely into Database.
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signUp(String email,String password)
    {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty())
        {
            User user=new User();
            user.setEmail(email);
            //Encrypting our password using bcryptpassword encoder
            user.setPassword(bCryptPasswordEncoder.encode(password));
            User saveduser=userRepository.save(user);
            return saveduser;
        }
        return userOptional.get();
    }

    public Pair<User,MultiValueMap<String,String>> login(String email, String password)
    {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty())
        {
            return null;
        }
        User user=userOptional.get();

      //  if(!user.getPassword().equals(password)) used this when there is no encrypted password
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) //when encryption is enabled for password attribute
        {
            return null;
        }

//        Code to Generate the JWT Token with only header and payload
//        Sample token message or payload content
//        String message = "{\n" +
//        "   \"email\": \"anurag@scaler.com\",\n" +
//        "   \"roles\": [\n" +
//        "      \"instructor\",\n" +
//        "      \"buddy\"\n" +
//        "   ],\n" +
//        "   \"expirationDate\": \"2ndApril2024\"\n" +
//        "}";
//        byte[] content=message.getBytes(StandardCharsets.UTF_8);
//        //This is HS256 Hashing algorithm to be used for Signature
//        MacAlgorithm algorithm=Jwts.SIG.HS256;
//        //This is the secret Key build using HS256 algorithm
//        SecretKey secret=algorithm.key().build();
//        //Now token has been included with payload(content) & signature(secret)
//        String token= Jwts.builder().content(content).signWith(secret).compact();
//        MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
//        headers.add(HttpHeaders.SET_COOKIE,token);
//        return new Pair<User,MultiValueMap<String,String>>(user,headers);

//      Generate Token for user specific data like username,roles,created,expiry of token
        Map<String,Object> jwtData=new HashMap<>();
        jwtData.put("email",user.getEmail());
        jwtData.put("roles",user.getRoles());
        long nowInMillis=System.currentTimeMillis();
        jwtData.put("createdAt",new Date(nowInMillis));
        jwtData.put("expiry",nowInMillis+100000L);

        //Here we used claims method to accept Hashmap as user Data
        String token=Jwts.builder().claims(jwtData).signWith(secret).compact();

        //Storing the token and user data in persistant session to retain
        Session session=new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        session.setExpiringAt(new Date(nowInMillis+100000L));
        sessionRepository.save(session);

        MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);
        return new Pair<User,MultiValueMap<String,String>>(user,headers);
    }

    //While validation, once you run the app first signup and then try to login then copy
    //the token from set_cookie,once the application stops and rerun, the secretkey will be
    //generated newly so when you check with previous run token signature it won't be matched.
    public boolean validateToken(String token,long userId)
    {
        System.out.println(token);
        System.out.println(userId);
        Optional<Session> optionalSession=sessionRepository.findByTokenAndUser_Id(token,userId);

        if(optionalSession.isEmpty())
        {
            System.out.println("No session found for user id");
            return false;
        }
        Session session= optionalSession.get();
        String storedToken= session.getToken();

        JwtParser jwtParser=Jwts.parser().verifyWith(secret).build();
        Claims claims=jwtParser.parseSignedClaims(storedToken).getPayload();

        long nowInMillis=System.currentTimeMillis();
        long expiryTime=claims.get("expiry", Long.class);

        if(nowInMillis>expiryTime)
        {
            System.out.println(nowInMillis);
            System.out.println(expiryTime);
            System.out.println("Token has expired");
        }

        Optional<User> optionalUser=userRepository.findById(userId);
        if(optionalUser.isEmpty())
        {
            System.out.println("User not found");
            return false;
        }
        String email=optionalUser.get().getEmail();
        if(!email.equals(claims.get("email")))
        {
            System.out.println(email);
            System.out.println(claims.get("email"));
            System.out.println("email do not match");
            return false;
        }
        return true;
    }

}
