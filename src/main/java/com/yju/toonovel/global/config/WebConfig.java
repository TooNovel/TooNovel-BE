package com.yju.toonovel.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${server.frontEnd}")
	private String frontEndServer;

	@Value("${server.backEnd}")
	private String backEndServer;

	@Value("${server.machineLearning}")
	private String machineLearningServer;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns(frontEndServer, backEndServer, machineLearningServer, "/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
			.allowedHeaders("*")
			.allowCredentials(true);
	}

}
