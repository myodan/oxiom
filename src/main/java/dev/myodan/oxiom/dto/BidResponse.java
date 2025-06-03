package dev.myodan.oxiom.dto;

import dev.myodan.oxiom.domain.User;

import java.time.Instant;

public record BidResponse(
        Long id,
        Long price,
        User createdBy,
        Instant createdDate
) {
}