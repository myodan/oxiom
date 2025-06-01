package dev.myodan.oxiom.dto;

import java.time.Instant;

public record ProductSummaryResponse(

        Long id,

        String name,

        String description,

        String thumbnailUrl,

        UserResponse highestBidder,

        Integer bidUnit,

        Integer initialPrice,

        Integer currentPrice,

        Instant endDate,

        CategoryResponse category,

        UserResponse createdBy,

        Instant createdDate

) {
}