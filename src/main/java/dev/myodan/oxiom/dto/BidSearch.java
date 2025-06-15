package dev.myodan.oxiom.dto;

import lombok.Builder;

@Builder
public record BidSearch(

        Long userId,

        Long productId

) {
}
