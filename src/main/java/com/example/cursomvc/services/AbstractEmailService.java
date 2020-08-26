package com.example.cursomvc.services;

import com.example.cursomvc.domain.Cliente;
import com.example.cursomvc.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@SuppressWarnings("unused")
public class AbstractEmailService implements EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${default-sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage smm = prepareSimpleMailMessaFromPedido(obj);
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

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = prepareMimeMessageMessaFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }

    }

    protected MimeMessage prepareMimeMessageMessaFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);

        return mimeMessage;
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {

    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage smm = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(smm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }
}
