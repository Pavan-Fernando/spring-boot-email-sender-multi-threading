package com.email.sender.multithreading.service.impl;

import com.email.sender.multithreading.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async("emailExecutor")
    public CompletableFuture<String> sendEmail(String email) {

        try {
            // 1️⃣ Build Thymeleaf context
            Context context = new Context();
            context.setVariable("name", "name");

            // 2️⃣ Generate HTML from template
            String htmlContent = templateEngine.process("email/welcome-email", context);

            // 3️⃣ Create MIME email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Welcome to Synergen Tech Lab!");
            helper.setText(htmlContent, true); // 'true' = send as HTML

            mailSender.send(message);

            log.error("HTML email sent successfully to: {}", email);
            return CompletableFuture.completedFuture("Sent to " + email);
        } catch (Exception e) {
            log.error("Failed to send email to: {} | {}", email, e.getMessage());
            return CompletableFuture.completedFuture("Failed to send to " + email);
        }
    }
}
