package dev.myodan.oxiom.dto;

import java.time.Instant;

import static dev.myodan.oxiom.domain.Product.Status;

public record ProductSummaryResponse(

        Long id,

        String name,

        String description,

        String thumbnailUrl,

        UserResponse highestBidder,

        Long bidUnit,

        Long initialPrice,

        Long currentPrice,

        Instant endDate,

        CategoryResponse category,

        Status status,

        UserResponse createdBy,

        Instant createdDate

) {
}