package com.yju.toonovel.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("TooNovel API Document")
			.description("API Document")
			.version("1.0");
	}
}
