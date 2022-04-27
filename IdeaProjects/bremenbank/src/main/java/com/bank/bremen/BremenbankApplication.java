package com.bank.bremen;

import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BremenbankApplication {

	public static void main(String[] args) {

		SpringApplication.run(BremenbankApplication.class, args);
	}
    @Bean
	public ModelMapper modelMapper(){
    ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE)
			.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
			return modelMapper;
   }
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");

			}
		};
}
}