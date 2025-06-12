package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record ProductImageResponse(

        Long id,

        String objectKey,

        Integer order

) {
}