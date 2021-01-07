package com.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })   : To discard the security auto-configuration and
// add our own configuration,
// we need to exclude the SecurityAutoConfiguration class.
@SpringBootApplication
public class SmartContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
	}

}
