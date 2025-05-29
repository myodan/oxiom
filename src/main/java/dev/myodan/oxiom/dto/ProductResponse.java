package dev.myodan.oxiom.dto;

import java.time.Instant;
import java.util.List;

public record ProductResponse(

        Long id,

        String title,

        String description,

        List<ProductImageResponse> images,

        UserResponse highestBidder,

        Integer bidUnit,

        Integer initialPrice,

        Integer currentPrice,

        Instant endDate,

        UserResponse createdBy,

        Instant createdDate

) {
}