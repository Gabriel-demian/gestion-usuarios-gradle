package com.bci.gestionusuarios.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@AllArgsConstructor
public class SecurityConfigurer {

    private AuthFilter authFilter;

	@Bean
	public SecurityFilterChain configSecurity(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
				.requestMatchers(unauthorizedEndpointsMatcher).permitAll()
				.anyRequest().authenticated();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	private static final RequestMatcher unauthorizedEndpointsMatcher = request -> request.getRequestURI().contains("/sign-up") || request.getRequestURI().contains("/h2-console");
}
