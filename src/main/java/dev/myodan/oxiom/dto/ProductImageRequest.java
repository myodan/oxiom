package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ProductImageRequest(

        @NotBlank
        String objectKey,

        @NotNull
        @PositiveOrZero
        Integer order

) {
}
