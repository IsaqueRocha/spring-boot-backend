package com.example.cursomvc.services;

import com.example.cursomvc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

@SuppressWarnings("unused")
public class AbstractEmailService implements EmailService {

    @Value("${default-sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage smm = prepareSimpleMailMessaFromPedido( obj);
        sendEmail(smm);
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {

    }

    protected SimpleMailMessage prepareSimpleMailMessaFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }
}
