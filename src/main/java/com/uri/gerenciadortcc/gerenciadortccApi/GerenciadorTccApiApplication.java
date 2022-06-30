package com.uri.gerenciadortcc.gerenciadortccApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class GerenciadorTccApiApplication implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/").allowedOrigins("GET","POST","PUT","DELETE","OPTIONS");
		registry.addMapping("/").allowedOrigins("http://localhost:8080/").allowedOrigins("http://localhost:3000","http://localhost:3001");
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorTccApiApplication.class, args);
	}

}
