package dev.myodan.oxiom.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User highestBidder;

    @Column(nullable = false)
    private Integer bidUnit;

    @Column(nullable = false)
    private Integer initialPrice;

    @Column(nullable = false)
    private Integer currentPrice;

    @Column(nullable = false)
    private Instant endDate;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @CreatedDate
    private Instant createdDate;

}
