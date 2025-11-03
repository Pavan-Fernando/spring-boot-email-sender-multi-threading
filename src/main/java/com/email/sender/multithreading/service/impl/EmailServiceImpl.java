package com.email.sender.multithreading.service.impl;

import com.email.sender.multithreading.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async("emailExecutor")
    public CompletableFuture<String> sendEmail(String email) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Welcome to Synergen Tech Lab!");
            message.setText("Hi there,\n\nThis is a test email from your Spring Boot async email sender.\n\nBest,\nPavan 🚀");

            mailSender.send(message);
            log.info("Email sent successfully to: {}", email);
            return CompletableFuture.completedFuture("Sent to " + email);
        } catch (Exception e) {
            log.error("Failed to send email to: {} | {}", email, e.getMessage());
            return CompletableFuture.completedFuture("Failed to send to " + email);
        }
    }
}
