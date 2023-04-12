package com.yju.toonovel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class ToonovelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToonovelApplication.class, args);
	}

}
