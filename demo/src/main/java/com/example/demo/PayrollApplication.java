package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class PayrollApplication {

	public static void main(String[] args) {
		System.out.println("DB_URL=" + System.getenv("DB_URL"));
        System.out.println("DB_USER=" + System.getenv("DB_USER"));
        System.out.println("DB_PASS=" + System.getenv("DB_PASS"));
		SpringApplication.run(PayrollApplication.class, args);
	}

	@Bean
CommandLineRunner printConfig(Environment env) {
    return args -> {
        System.out.println("URL = " + env.getProperty("spring.datasource.url"));
        System.out.println("USER = " + env.getProperty("spring.datasource.username"));
        System.out.println("PASS = " + env.getProperty("spring.datasource.password"));
    };
}

}
