package com.saman.Form;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.saman.Form")
public class DynamicFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicFormApplication.class, args);
	}

}
