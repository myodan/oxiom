package dev.myodan.oxiom.dto;

import dev.myodan.oxiom.domain.Product.Status;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ProductResponse(

        Long id,

        String name,

        String description,

        String thumbnail,

        List<ProductImageResponse> images,

        UserResponse highestBidder,

        Long bidUnit,

        Long initialPrice,

        Long currentPrice,

        Instant endDate,

        CategoryResponse category,

        Status status,

        UserResponse createdBy,

        Instant createdDate,

        Instant lastModifiedDate

) {
}