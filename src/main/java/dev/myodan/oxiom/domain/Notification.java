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
public class Notification {

    @Id
    private String id;

    private Long userId;

    private String content;

    @Builder.Default
    private Boolean isRead = false;

    @CreatedDate
    private Instant createdDate;

}
