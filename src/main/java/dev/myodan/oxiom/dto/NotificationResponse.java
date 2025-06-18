package dev.myodan.oxiom.dto;

import java.io.Serializable;
import java.time.Instant;

public record NotificationResponse(

        String id,

        Long userId,

        String content,

        Boolean isRead,

        Instant createdDate

) implements Serializable {
}