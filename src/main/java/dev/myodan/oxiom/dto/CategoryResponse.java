package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(

        Long id,

        String name

) {
}