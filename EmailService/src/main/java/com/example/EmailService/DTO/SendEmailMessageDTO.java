package com.example.EmailService.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailMessageDTO {
    private String to;
    private String from;
    private String subject;
    private String body;
}
