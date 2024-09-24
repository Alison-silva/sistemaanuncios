package com.alison.sistemaanuncios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SistemaanunciosApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SistemaanunciosApplication.class, args);

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/templates/**", "/resources/**", "/static/**", "/assets/**", "/css/**",
						"classpath:/static/", "classpath:/resources/")
				.addResourceLocations("/resources/",
						"classpath:/static/**", "classpath:/static/", "classpath:/static/assets/",
						"classpath:/resources/", "classpath:/static/css/", "/resources/**",
						"/WEB-INF/classes/static/**");
	}

}
