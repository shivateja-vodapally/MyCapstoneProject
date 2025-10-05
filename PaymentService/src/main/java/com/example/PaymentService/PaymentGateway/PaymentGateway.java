package com.example.PaymentService.PaymentGateway;

public interface PaymentGateway {
    public String generatePaymentLink(String orderId,String email,String phoneNumber,Long amount);
}
