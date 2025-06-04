package dev.myodan.oxiom.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document
public class ChatMessage {

    @Id
    private String id;

    private Long roomId;

    private Long senderId;

    private String content;

    @CreatedDate
    private Instant createDate;

}
