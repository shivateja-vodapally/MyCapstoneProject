package com.example.PaymentService.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDTO {
    private String orderId;
    private String email;
    private String phoneNumber;
    private Long amount;
}
