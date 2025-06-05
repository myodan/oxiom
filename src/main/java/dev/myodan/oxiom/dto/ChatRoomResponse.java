package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ChatRoomResponse(

        Long id,

        UserResponse user1,

        UserResponse user2,

        ChatMessageResponse lastChatMessage,

        Instant createdDate

) {
}