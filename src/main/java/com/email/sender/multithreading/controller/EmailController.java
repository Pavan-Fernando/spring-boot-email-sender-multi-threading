package com.email.sender.multithreading.controller;

import com.email.sender.multithreading.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${base-url.context}/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendBulkEmails(@RequestBody List<String> emails) {
        // Trigger all async email tasks
        List<CompletableFuture<String>> futures = emails.stream()
                .map(emailService::sendEmail)
                .toList();

        // Combine all futures and wait for them to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Collect results
        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        return """
               All emails processed!
               -----------------------------
               %s
               """.formatted(String.join("\n", results));
    }
}
