package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record UserUpdateRequest(

        String displayName,

        String avatarUrl

) {
}
