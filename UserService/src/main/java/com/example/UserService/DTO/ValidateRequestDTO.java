package com.example.UserService.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequestDTO {
    private String token;
    private long userId;
}
