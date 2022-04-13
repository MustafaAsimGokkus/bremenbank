package com.bank.bremen.security;

import com.bank.bremen.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bremenbank.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bremenbank.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token unsupperted {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT Token  {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid SWT Signature {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT illegal argument exception {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }


}