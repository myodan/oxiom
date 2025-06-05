package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ChatMessageRequest(

        @NotBlank
        String content

) {
}
