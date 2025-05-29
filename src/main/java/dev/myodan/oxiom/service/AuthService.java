package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.User;
import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.TokenRequest;
import dev.myodan.oxiom.dto.TokenResponse;
import dev.myodan.oxiom.repository.UserRepository;
import dev.myodan.oxiom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public TokenResponse issueToken(TokenRequest tokenRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenRequest.username(), tokenRequest.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String accessToken = jwtUtil.issueToken(user);
        String refreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(KeyGenerators.secureRandom(32).generateKey());
        redisTemplate.opsForValue().set("refresh-token:" + refreshToken, user.getId(), Duration.ofDays(7));

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse refreshToken(String refreshToken) {
        Long userId = (Long) Optional.ofNullable(redisTemplate.opsForValue().get("refresh-token:" + refreshToken)).orElseThrow(
                () -> new IllegalArgumentException("Invalid refresh token.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found.")
        );

        String newAccessToken = jwtUtil.issueToken(user);
        String newRefreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(KeyGenerators.secureRandom(32).generateKey());
        redisTemplate.delete("refresh-token:" + refreshToken);
        redisTemplate.opsForValue().set("refresh-token:" + newRefreshToken, user.getId(), Duration.ofDays(7));

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
