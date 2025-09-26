package com.example.UserService.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseModel{
    //Here Role can be anything that's why we have given attribute instead of enum. It can be
    //user,admin,customer,etc..
    private String value;
}
