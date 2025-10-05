package com.example.PaymentService.Config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.key.id}") //getting value from application.properties
    private String razorpayId;
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;
    @Bean
    public RazorpayClient getRazorPayClient() throws RazorpayException
    {
        return new RazorpayClient(razorpayId,razorpaySecret);
    }
}
