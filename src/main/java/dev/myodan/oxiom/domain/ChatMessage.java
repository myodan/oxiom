package dev.myodan.oxiom.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document
@CompoundIndexes({
        @CompoundIndex(name = "chatRoomId_createDate_idx", def = "{'chatRoomId': 1, 'createDate': -1}")
})
public class ChatMessage {

    @Id
    private String id;

    private Long chatRoomId;

    private Long senderId;

    private String content;

    @CreatedDate
    private Instant createdDate;

}
