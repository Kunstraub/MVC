package com.exercise.MVC.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String generateToken(String userMail){
        Instant now = Instant.now();
        Instant expiration = now.plus(3, ChronoUnit.HOURS);

        return Jwts.builder()
                .setSubject(userMail)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();
    }

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return generateToken(user.getUsername());
    }

    public String getUserMailFromToken(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e) {
            log.error("Invalid JWT signature");
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        }
        catch (ExpiredJwtException e) {
            log.error("Expired Jwt token");
        }
        catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt token");
        }
        catch (IllegalArgumentException e) {
            log.error("Jwt claims string is empty");
        }
        return false;
    }
}
