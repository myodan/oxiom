package dev.myodan.oxiom.dto;

import dev.myodan.oxiom.domain.Product.Status;

import java.time.Instant;

public record UserProductResponse(

        Long id,

        String name,

        String description,

        String thumbnail,

        UserResponse highestBidder,

        Long bidUnit,

        Long initialPrice,

        Long currentPrice,

        Instant endDate,

        CategoryResponse category,

        Status status,

        Instant createdDate,

        Instant lastModifiedDate

) {
}
