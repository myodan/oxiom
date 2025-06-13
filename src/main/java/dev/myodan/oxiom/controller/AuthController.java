package dev.myodan.oxiom.controller;

import dev.myodan.oxiom.dto.TokenRequest;
import dev.myodan.oxiom.dto.TokenResponse;
import dev.myodan.oxiom.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.cookie-secure}")
    private boolean cookieSecure;

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> issueToken(@RequestBody @Valid TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.issueToken(tokenRequest);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken")
                .value(tokenResponse.refreshToken())
                .path("/")
                .maxAge(Duration.ofDays(7))
                .httpOnly(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(tokenResponse);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@CookieValue String refreshToken) {
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken")
                .value(tokenResponse.refreshToken())
                .path("/")
                .maxAge(Duration.ofDays(7))
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(Cookie.SameSite.LAX.name())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(tokenResponse);
    }

    @PostMapping("/token/invalidate")
    public ResponseEntity<Void> invalidateToken(@CookieValue String refreshToken) {
        authService.invalidateToken(refreshToken);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(Cookie.SameSite.LAX.name())
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()).body(null);
    }

}
