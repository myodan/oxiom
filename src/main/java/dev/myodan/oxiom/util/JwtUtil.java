package dev.myodan.oxiom.util;

import dev.myodan.oxiom.domain.User;
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

    private static final String JWT_USER_ID_KEY = "uid";
    private static final String JWT_USERNAME_KEY = "uname";
    private static final String JWT_ROLE_KEY = "role";
    private static final String JWT_EMAIL_KEY = "email";
    private static final String JWT_AVATAR_URL_KEY = "avatar";

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

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get(JWT_USER_ID_KEY, Long.class));
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get(JWT_USERNAME_KEY, String.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get(JWT_ROLE_KEY, String.class));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get(JWT_EMAIL_KEY, String.class));
    }

    public String extractAvatarUrl(String token) {
        return extractClaim(token, claims -> claims.get(JWT_AVATAR_URL_KEY, String.class));
    }

    public User validateToken(String token) {
        try {
            return User.builder()
                    .id(this.extractUserId(token))
                    .username(this.extractUsername(token))
                    .role(User.Role.valueOf(this.extractRole(token)))
                    .email(this.extractEmail(token))
                    .avatarUrl(this.extractAvatarUrl(token))
                    .build();
        } catch (JwtException exception) {
            return null;
        }
    }

    public String issueToken(User user) {
        return Jwts.builder()
                .claim(JWT_USER_ID_KEY, user.getId())
                .claim(JWT_USERNAME_KEY, user.getUsername())
                .claim(JWT_ROLE_KEY, user.getRole().name())
                .claim(JWT_EMAIL_KEY, user.getEmail())
                .claim(JWT_AVATAR_URL_KEY, user.getAvatarUrl())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(this.getSecretKey())
                .compact();
    }

}