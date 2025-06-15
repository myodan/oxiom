package dev.myodan.oxiom.repository;

import dev.myodan.oxiom.domain.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("""
            select b
            from Bid b
            where
                (:userId is null or b.createdBy.id = :userId) and
                (:productId is null or b.product.id = :productId)
            """)
    @EntityGraph(attributePaths = {"product", "createdBy"})
    Page<Bid> findAll(Long userId, Long productId, Pageable pageable);

}
