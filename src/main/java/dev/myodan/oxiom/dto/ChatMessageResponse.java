package dev.myodan.oxiom.dto;

import java.time.Instant;

public record ChatMessageResponse(

        String id,

        Long roomId,

        Long senderId,

        String content,

        Instant createTime

) {
}