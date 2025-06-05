package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ChatRoomRequest(

        @NotNull
        @Positive
        Long userId

) {
}
