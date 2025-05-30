package com.example.Chess.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void sendVerificationEmail(String toEmail, String token) {
        String link = env.getProperty("backend.ngrok.api") + "/auth/verified/" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Email Verification");
        message.setText("Click to verify and complete your registration: " + link);
        message.setFrom("ahmadfgl1230@gmail.com"); //TODO: create checks.com account
        mailSender.send(message);
    }
}