package dev.myodan.oxiom.dto;

import java.time.Instant;

public record BidResponse(
        Long id,
        Long price,
        UserResponse createdBy,
        Instant createdDate
) {
}