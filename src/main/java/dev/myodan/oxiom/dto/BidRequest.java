package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BidRequest(

        @NotNull
        @Positive
        Long price

) {
}
