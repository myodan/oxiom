package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record TokenResponse(

        String accessToken,

        String refreshToken

) {
}
