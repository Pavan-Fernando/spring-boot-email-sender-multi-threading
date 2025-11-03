package com.email.sender.multithreading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // number of active threads
        executor.setMaxPoolSize(10); // maximum threads
        executor.setQueueCapacity(25); // queue before rejecting
        executor.setThreadNamePrefix("EmailThread-");
        executor.initialize();
        return executor;
    }
}
