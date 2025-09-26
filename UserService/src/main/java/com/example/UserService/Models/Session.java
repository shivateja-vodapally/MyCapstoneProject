package com.example.UserService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Session extends BaseModel{
    private String token;
    private Date expiringAt;
    @ManyToOne
    private User user; //one user can have many sessions
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
