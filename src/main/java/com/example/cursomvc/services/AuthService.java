package com.example.cursomvc.services;

import com.example.cursomvc.domain.Cliente;
import com.example.cursomvc.repositories.ClienteRepository;
import com.example.cursomvc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(bCrypt.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }

        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt();
        if (opt == 0) {
            return (char) (rand.nextInt(10) + 48); /* Gera um dígito */
        } else if (opt == 1) {
            return (char) (rand.nextInt(26) + 65); /* Gera uma letra maiúscula */
        } else {
            return (char) (rand.nextInt(26) + 65); /* Gera uma letra minúscula */
        }
    }
}
