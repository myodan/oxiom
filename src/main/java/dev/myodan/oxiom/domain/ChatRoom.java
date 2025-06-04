package dev.myodan.oxiom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "chat_room",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user1_id", "user2_id"})
        }
)
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

}
