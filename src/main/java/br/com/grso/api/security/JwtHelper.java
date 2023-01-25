package br.com.grso.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHelper {
    @Value(value = "${security.jwt.secret}")
    private String secret;
    @Value(value = "${security.jwt.expiration}")
    private Long expiration;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setExpiration(this.generateExpirationDate()).signWith(SignatureAlgorithm.HS512, this.secret.getBytes()).compact();
    }

    public String extractUsernameFromToken(String jwtToken) {
        Claims claims = this.getTokenClaims(jwtToken);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public boolean isTokenValid(String jwtToken) {
        Claims claims = this.getTokenClaims(jwtToken);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date actualTime = new Date(System.currentTimeMillis());
            return username != null && expirationDate != null && actualTime.before(expirationDate);
        }
        return false;
    }

    private Claims getTokenClaims(String jwtToken) {
        try {
            return Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(jwtToken).getBody();
        } catch (Exception exception) {
            return null;
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration);
    }
}
