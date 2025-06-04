package dev.myodan.oxiom.dto;

import java.time.Instant;

public record FavoriteResponse(

        Long id,

        ProductSummaryResponse product,

        Instant createdDate

) {
}