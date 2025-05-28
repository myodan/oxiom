package dev.myodan.oxiom.service;

import dev.myodan.oxiom.domain.UserPrincipal;
import dev.myodan.oxiom.dto.TokenRequest;
import dev.myodan.oxiom.dto.TokenResponse;
import dev.myodan.oxiom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public TokenResponse issueToken(TokenRequest tokenRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenRequest.username(), tokenRequest.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String accessToken = jwtUtil.issueToken(userPrincipal);
        String refreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(KeyGenerators.secureRandom(32).generateKey());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
