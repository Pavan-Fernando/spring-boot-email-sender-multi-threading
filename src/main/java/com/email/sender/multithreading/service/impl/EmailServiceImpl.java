package com.email.sender.multithreading.service.impl;

import com.email.sender.multithreading.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {


    @Override
    @Async("emailExecutor")
    public CompletableFuture<String> sendEmail(String email) {
        try {
            log.info("📤 Sending email to {}", email);
            Thread.sleep(2000 + (int)(Math.random() * 2000)); // simulate random delay
            log.info("✅ Email successfully sent to {}", email);
            return CompletableFuture.completedFuture("✅ Sent to " + email);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("❌ Error sending email to {}", email, e);
            return CompletableFuture.completedFuture("❌ Failed to send to " + email);
        }
    }
}
