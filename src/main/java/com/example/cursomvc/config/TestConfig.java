package com.example.cursomvc.config;

import com.example.cursomvc.services.DBService;
import com.example.cursomvc.services.EmailService;
import com.example.cursomvc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDataBase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
