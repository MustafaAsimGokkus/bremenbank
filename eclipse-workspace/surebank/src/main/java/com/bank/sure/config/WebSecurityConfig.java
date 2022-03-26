package com.bank.sure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bank.sure.security.AuthTokenFilter;
import com.bank.sure.security.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Super;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
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
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}
	
}
