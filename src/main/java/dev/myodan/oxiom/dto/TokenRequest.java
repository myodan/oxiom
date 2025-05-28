package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TokenRequest(

        @NotNull
        @NotBlank
        String username,

        @NotNull
        @NotBlank
        String password

) {
}
