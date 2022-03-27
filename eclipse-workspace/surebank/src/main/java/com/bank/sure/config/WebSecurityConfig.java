package com.bank.sure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bank.sure.security.AuthEntryPointJwt;
import com.bank.sure.security.AuthTokenFilter;
import com.bank.sure.security.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Super;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	private UserDetailsServiceImpl userDetailsServiceImpl;
	private final AuthEntryPointJwt unauthorizedHandler;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter authenticationJwtTokeFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	protected  AuthenticationManager authenticationManager()throws Exception{
		return super.authenticationManager();
	}
	
	//we connect our class with the security structure
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceImpl);
		}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/register","/login")
		.permitAll()
		.anyRequest().authenticated();
		
	http.addFilterBefore(authenticationJwtTokeFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
}
