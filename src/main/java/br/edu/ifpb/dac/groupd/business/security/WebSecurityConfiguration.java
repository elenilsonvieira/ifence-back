package br.edu.ifpb.dac.groupd.business.security;


import static br.edu.ifpb.dac.groupd.business.service.RoleService.AVAILABLE_ROLES.ADMIN;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.edu.ifpb.dac.groupd.business.service.interfaces.PasswordEncoderService;
import br.edu.ifpb.dac.groupd.business.service.interfaces.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenRequestFilter requestFilter;
	
	@Autowired
	private PasswordEncoderService passwordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
			.passwordEncoder(passwordEncoder);
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers(
				"/resources/**",
				"/v2/api-docs",
				"/v3/api-docs",
				"/v3/api-docs/**",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/swagger-ui.html",
				"/webjars/**"
			);
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf()
				.disable()
			.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED))
			.and()
				.authorizeRequests()
					// Swagger / OpenAPI e H2 liberados (dev)
					.antMatchers(
						"/v2/api-docs",
						"/v3/api-docs",
						"/v3/api-docs/**",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/swagger-resources/**",
						"/webjars/**",
						"/h2-console/**"
					).permitAll()
					.antMatchers(GET, "/actuator").permitAll()
					// Endpoints públicos (com e sem prefixo /api)
					.antMatchers(POST, 
						"/api/signin", "/signin",
						"/api/users", "/users",
						"/api/login", "/login",
						"/api/isValidToken", "/isValidToken"
					).permitAll()
					// Demais recursos autenticados (com e sem prefixo /api)
					.antMatchers(
						"/api/users/**", "/users/**",
						"/api/bracelets", "/api/bracelets/**", "/bracelets", "/bracelets/**",
						"/api/fences", "/api/fences/**", "/fences", "/fences/**",
						"/api/locations", "/api/locations/**", "/locations", "/locations/**",
						"/api/alarms/**", "/alarms/**"
					).authenticated()
					.antMatchers(DELETE, "/api/users").hasRole(ADMIN.toString())
					.anyRequest().authenticated()
			.and()
				.sessionManagement()
					.sessionCreationPolicy(STATELESS)
				.and()
				.headers()
					.frameOptions().sameOrigin()
				.and()
				.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(
					logout ->
						logout
						.clearAuthentication(true)
						.logoutUrl("/api/logout")
						.logoutSuccessUrl("/api/login")
						.invalidateHttpSession(true)
						.logoutSuccessHandler((request, response, authentication) -> {})
				);
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration corsConfig = new CorsConfiguration();
	    
	    List<String> all = Arrays.asList("*");
	    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	    corsConfig.setAllowedMethods(all);
	    corsConfig.setAllowedOriginPatterns(all);
	    corsConfig.setAllowedHeaders(all);
	    corsConfig.setAllowCredentials(true);
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig);
	    return source;
	}
	
}
