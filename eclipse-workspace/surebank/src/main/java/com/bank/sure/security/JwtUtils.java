package com.bank.sure.security;

import java.util.Date;


import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentRendererTokenTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bank.sure.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
   
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${surebank.app.jwtSecret}")
    private String jwtSecret;
    @Value("${surebank.app.jwtExpirationMs}")
    private long jwtExpirationMs;
	
    public String generateToken (Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
	
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
			    .signWith(SignatureAlgorithm.HS512, jwtSecret)
			    .compact();
	}
	
    public boolean validateToken (String token) {
    	
    	try {
    	Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    	return true;
    	}catch (ExpiredJwtException e) {
    		LOGGER.error("JWT Token expired{}",e.getMessage());
    	}catch (MalformedJwtException e) {
    		LOGGER.error("JWT Token Malformed{}",e.getMessage());
    	}catch (UnsupportedJwtException e) {
    		LOGGER.error("JWT Token unsupported{}",e.getMessage());
    	}catch ( SignatureException e) {
    		LOGGER.error("Invalid JWT signature{}",e.getMessage());
    	}catch (IllegalArgumentException e) {
    		LOGGER.error("JWT Token illegal argument exception {}",e.getMessage());
    	}
    	return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
    	return Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody()
    			.getSubject();
    }
    
}
