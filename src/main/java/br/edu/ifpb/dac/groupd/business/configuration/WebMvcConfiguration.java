package br.edu.ifpb.dac.groupd.business.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// Adicionar "/api" apenas para nossos controllers, não para controllers de libs (ex.: springdoc)
		configurer.addPathPrefix(
			"api",
			HandlerTypePredicate.forBasePackage("br.edu.ifpb.dac.groupd")
				.and(HandlerTypePredicate.forAnnotation(RestController.class))
		);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			// Em dev, liberar padrões para Expo Web e LAN. Para prod, restringir domínios.
			.allowedOriginPatterns("*")
			.allowedHeaders("*")
			.allowedMethods("*")
			.allowCredentials(true);
	}
}