package com.example.cursomvc.services;

import com.example.cursomvc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

@SuppressWarnings("unused")
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}
