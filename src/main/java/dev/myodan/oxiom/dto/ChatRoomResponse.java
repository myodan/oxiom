package dev.myodan.oxiom.dto;

import java.time.Instant;

public record ChatRoomResponse(

        Long id,

        UserResponse user1,

        UserResponse user2,

        Instant createdDate
) {
}