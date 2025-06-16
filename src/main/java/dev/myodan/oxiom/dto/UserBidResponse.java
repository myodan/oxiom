package dev.myodan.oxiom.dto;

import java.time.Instant;

public record UserBidResponse(

        Long id,

        ProductSummaryResponse product,

        Long price,

        Instant createdDate

) {
}
