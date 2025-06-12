package dev.myodan.oxiom.dto;

import dev.myodan.oxiom.domain.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String thumbnailUrl,

        @NotNull
        List<@Valid ProductImageRequest> images,

        @NotNull
        @Positive
        Long bidUnit,

        @NotNull
        @PositiveOrZero
        Long initialPrice,

        @NotNull
        @Future
        Instant endDate,

        @NotNull
        Category category

) {
}
