package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PayrollApplication.class)
class PayrollApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("DB_URL=" + System.getenv("DB_URL"));
    	System.out.println("DB_USER=" + System.getenv("DB_USER"));
    	System.out.println("DB_pass=" + System.getenv("DB_pass"));
	}

}
