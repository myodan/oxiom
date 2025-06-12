package dev.myodan.oxiom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "product_image",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "order"})
        }
)
public class ProductImage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Product product;

    @Column(columnDefinition = "text", nullable = false)
    private String objectKey;

    @Column(nullable = false)
    private Integer order;

}
