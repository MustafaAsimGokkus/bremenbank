package com.bank.sure.security;

import java.util.Date;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentRendererTokenTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.sure.security.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    @Value("${surebank.app.jwtSecret}")
    private String jwtSecret;
    @Value("${surebank.app.jwtExpirationMs}")
    private long jwtExpirationMs;
	
    public String generateToken (Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
	
		return Jwts.builder().
				setSubject(userPrincipal.getUsername()).
				setIssuedAt(new Date()).
				setExpiration(new Date((new Date()).getTime()+jwtExpirationMs).
			    signWith(SignatureAlgorithm.HS512, jwtSecret).
			    compact();
	
	}
	
}
