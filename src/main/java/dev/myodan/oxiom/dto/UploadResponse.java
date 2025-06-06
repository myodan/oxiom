package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record UploadResponse(

        String url,

        String key,

        Long expiresIn

) {
}
