package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TokenRequest(

        @NotBlank
        String username,

        @NotBlank
        String password

) {
}
