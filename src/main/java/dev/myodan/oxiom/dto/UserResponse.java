package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record UserResponse(

        Long id,

        String username,

        String email,

        String displayName,

        String avatarUrl

) {
}