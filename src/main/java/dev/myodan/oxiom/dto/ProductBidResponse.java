package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ProductBidResponse(

        Long id,

        Long price,

        UserResponse createdBy,

        Instant createdDate

) {
}
