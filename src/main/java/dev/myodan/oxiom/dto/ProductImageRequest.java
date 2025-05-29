package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record ProductImageRequest(

        @NotBlank
        @URL
        String url,

        @NotNull
        @PositiveOrZero
        Integer order

) {
}
