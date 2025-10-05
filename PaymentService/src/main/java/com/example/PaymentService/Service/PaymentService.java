package com.example.PaymentService.Service;

import com.example.PaymentService.DTO.InitiatePaymentDTO;
import com.example.PaymentService.PaymentGateway.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentGateway razorpayPaymentGateway;

    public String initiatePayment(String orderId,String email,String phoneNumber,Long amount) {
        return razorpayPaymentGateway.generatePaymentLink(orderId,email,phoneNumber,amount);
    }
}
