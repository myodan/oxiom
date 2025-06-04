package dev.myodan.oxiom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChatRoomRequest(

        @NotNull
        @Positive
        Long userId

) {
}
