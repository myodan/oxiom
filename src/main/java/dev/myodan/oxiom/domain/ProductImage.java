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

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer order;

}
