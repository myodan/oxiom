package dev.myodan.oxiom.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ProductRequest(

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotNull
        List<@Valid ProductImageRequest> images,

        @NotNull
        @Positive
        Integer bidUnit,

        @NotNull
        @Positive
        Integer initialPrice,

        @NotNull
        @Future
        Instant endDate

) {
}
