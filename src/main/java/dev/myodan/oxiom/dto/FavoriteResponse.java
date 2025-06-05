package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record FavoriteResponse(

        Long id,

        ProductSummaryResponse product,

        Instant createdDate

) {
}