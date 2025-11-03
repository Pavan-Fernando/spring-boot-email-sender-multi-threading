package com.email.sender.multithreading.service;

import java.util.concurrent.CompletableFuture;

public interface EmailService {

    CompletableFuture<String> sendEmail(String email);
}
