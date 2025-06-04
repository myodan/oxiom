package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(

        @NotBlank
        String content

) {
}
