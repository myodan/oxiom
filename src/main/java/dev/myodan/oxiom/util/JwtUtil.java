package dev.myodan.oxiom.util;

import dev.myodan.oxiom.domain.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String JWT_USERNAME_KEY = "username";
    private static final String JWT_ROLE_KEY = "role";
    private static final String JWT_EMAIL_KEY = "email";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private Integer expirationMs;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(this.getSecretKey()).build();
    }

    private Claims extractAllClaims(String token) {
        return this.getJwtParser().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get(JWT_ROLE_KEY, String.class));
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }

    public String issueToken(UserPrincipal userPrincipal) {
        return Jwts.builder()
                .claim(JWT_ROLE_KEY, userPrincipal.getAuthorities().iterator().next().getAuthority())
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(this.getSecretKey())
                .compact();
    }

}