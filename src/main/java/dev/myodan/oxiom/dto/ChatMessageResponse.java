package dev.myodan.oxiom.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ChatMessageResponse(

        String id,

        Long chatRoomId,

        Long senderId,

        String content,

        Instant createdDate

) {
}