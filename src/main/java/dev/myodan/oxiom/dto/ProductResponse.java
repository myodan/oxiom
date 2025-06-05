package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ProductResponse(

        Long id,

        String name,

        String description,

        String thumbnailUrl,

        List<ProductImageResponse> images,

        UserResponse highestBidder,

        Long bidUnit,

        Long initialPrice,

        Long currentPrice,

        Instant endDate,

        CategoryResponse category,

        UserResponse createdBy,

        Instant createdDate

) {
}