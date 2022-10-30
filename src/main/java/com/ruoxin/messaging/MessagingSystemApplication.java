package com.ruoxin.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingSystemApplication {

	public static void main(String[] args) {
		// socket
		SpringApplication.run(MessagingSystemApplication.class, args);
	}

}
