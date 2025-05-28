package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record TokenResponse(

        String accessToken,

        String refreshToken

) implements Serializable {
}
