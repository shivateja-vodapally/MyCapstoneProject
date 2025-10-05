package com.example.PaymentService.Controller;

import com.example.PaymentService.DTO.InitiatePaymentDTO;
import com.example.PaymentService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public String initiatePayment(InitiatePaymentDTO initiatePaymentDTO)
    {
        return paymentService.initiatePayment(initiatePaymentDTO.getOrderId(),initiatePaymentDTO.getEmail(),initiatePaymentDTO.getPhoneNumber(), initiatePaymentDTO.getAmount());
    }
}
