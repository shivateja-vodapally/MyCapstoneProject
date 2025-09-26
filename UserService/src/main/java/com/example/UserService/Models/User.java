package com.example.UserService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseModel{
    private String email;
    private String password;
    //user can have any number of roles like learner,instructor,admin etc
    //one role can be attributed any number of users so many to many relationship
    @ManyToMany
    private Set<Role> roles=new HashSet<>();
}
