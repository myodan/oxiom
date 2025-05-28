package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(

        Long id,

        String username,

        String email,

        Instant createdDate,

        Instant lastModifiedDate

) {
}