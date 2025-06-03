package dev.myodan.oxiom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "text", nullable = false)
    private String password;

    @Column(columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "text")
    private String avatarUrl;

    @Column(columnDefinition = "text")
    private String displayName;

    @Column(columnDefinition = "text")
    private String bio;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public enum Role {
        ROLE_ADMIN,
        ROLE_USER
    }

}
