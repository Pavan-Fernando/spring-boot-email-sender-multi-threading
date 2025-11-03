package com.email.sender.multithreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
@EnableAsync
public class MultithreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingApplication.class, args);
	}

}
